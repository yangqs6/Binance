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
    public RestTemplate restTemplate;

    @Autowired
    private TransferService transferService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CalculateService calculateService;


    @Value("${url}")
    public String urlTemplate;

    @Value("${number}")
    public int number;

    public int testNumber(){
        System.out.println(number);
        return number;
    }

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
        String[][] result = response.getBody();
        assert result != null;
        List<RawKline> rawKlines = Arrays.stream(result)
                .map(part -> new RawKline(part, symbol, loadId))
                .collect(Collectors.toList());
        loadMapper.load(rawKlines);
        transferService.batchTransferAndLoad(rawKlines);
        if(endTime - startTime >= (60 * 1000 * 500)) {
            load(symbol, startTime + (60 * 1000 * 500), endTime, loadId);
        }
        return loadId;
    }

    public List<Kline> loadRedis(String symbol, Long startTime, Long endTime, String loadId, int frequency) throws JsonProcessingException {

        Long t = System.currentTimeMillis();
        List<Kline> result;
        //exist key?
        if(redisService.exists(symbol,loadId,startTime,endTime,frequency)) {
            result =redisService.fetchCalculate(symbol, loadId, startTime, endTime, frequency);
        }else{
            System.out.println("Not exists");
           List<Kline> klines = calculateService.getKline(symbol,startTime,endTime,loadId);
           result = calculateService.calculate(klines,symbol,loadId,frequency);
           redisService.loadCalculate(result,symbol,loadId,startTime,endTime,frequency);
       }
        System.out.println(System.currentTimeMillis()-t);
        return result;
    }
}



