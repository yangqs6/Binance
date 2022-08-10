package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.mapper.LoadMapper;
import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Transactional
@Service
@Scope("prototype")
public class LoadServiceImpl implements LoadService {


    static Object obj = new Object();

    @Autowired
    private LoadMapper loadMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransferService transferService;


    @Autowired
    private RedisService redisService;


    @Autowired
    private CalculateService calculateService;


    @Value("${url}")
    private String urlTemplate;

    public void batchLoad(List<RawKline> rawKlines) {
        loadMapper.load(rawKlines);
    }

    public void loadAll(Long startTime, Long endTime, String loadId) {
        for (MoneyType e : MoneyType.values()) {
            load(e, startTime, endTime, loadId);
        }
    }

    public void loadAllAsync(Long startTime, Long endTime, String loadId){
        ExecutorService es = Executors.newFixedThreadPool(MoneyType.values().length);
        for(MoneyType m: MoneyType.values()){
            Runnable syncRunnable = () -> load(m,startTime,endTime,loadId);
            es.execute(syncRunnable);
        }
        es.shutdown();
    }

    public String load(MoneyType symbol, Long startTime, Long endTime, String loadId) {

        if(endTime <= startTime){
            return loadId;
        }
        String url = String.format(urlTemplate, symbol, startTime, endTime);
        // get data from web
        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);
        String[][] result = response.getBody(); //500??? 这个是网页限制的访问么？
        assert result != null;
        List<RawKline> rawKlines = Arrays.stream(result)
                .map(part -> new RawKline(part, symbol, loadId))
                .collect(Collectors.toList());
        loadMapper.load(rawKlines);
        transferService.batchTransferAndLoad(rawKlines);
//        //TODO recursion -> for
        if(endTime - startTime >= (60 * 1000 * 500)) {
            load(symbol, startTime + (60 * 1000 * 500), endTime, loadId);
        }

        return loadId;
    }

    public List<Kline> loadRedis(String symbol, Long startTime, Long endTime, String loadId, int frequency) throws JsonProcessingException {
        // load data with 5 10 15 frequency...

        // if we have matched data in redis, stop
        // if we don't have matched data in redis,
        // first calculate , second load to redis

        Long t = System.currentTimeMillis();
        List<Kline> result;
        //exist key?
        if(redisService.exists(symbol,loadId,startTime,endTime,frequency)) {
            // exist --> fetch
            // no --> load
            result =redisService.fetchCalculate(symbol, loadId, startTime, endTime, frequency);
        }
       else{
           List<Kline> klines = calculateService.getKline(symbol,startTime,endTime,loadId);
           result = calculateService.calculate(klines,symbol,loadId,frequency);
           redisService.loadCalculate(result,symbol,loadId,startTime,endTime,frequency);
           //public void loadCalculate(List<Kline> klines, String symbol, String loadId,Long startTime, Long endTime, int frequency) throws JsonProcessingException {
       }
//        List<Kline> result2 = redisService.fetchCalculate(symbol, loadId, startTime, endTime, frequency);
//        for(Kline k :result2){
//            System.out.println(k);
//        }
        System.out.println(System.currentTimeMillis()-t);
        return result;
    }

    public int add(int a,int b){
        System.out.println("running add");
        return a + b;
    }


}



//  TODO to a list of new object from previous step. YYYY-mm-dd iso hh:ii:ss ISO format

// TODO unit test and integration test
//  mock vs spy

// TODO Calculate() -> List<Kline>(Not Trade) symbol, load id, start time , end time, frequency
// 1 []
// 2 []
// 3 []
// ...
// 5 []