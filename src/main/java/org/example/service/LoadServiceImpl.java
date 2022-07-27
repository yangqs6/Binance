package org.example.service;

import org.example.mapper.LoadMapper;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private LoadMapper loadMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TransferService transferService;

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
        String urlTemp = "localhost:8080/load/%s/%s/%s";
        for (MoneyType e : MoneyType.values()) {
            String url = String.format(urlTemp,e.toString(),startTime,endTime);
            
            // send request but no response needed
            // executor service https://www.baeldung.com/java-executor-service-tutorial
            //restTemplate.(url, String[][].class);
            //https://www.baeldung.com/thread-pool-java-and-guava

        }
    }

    public String load(MoneyType symbol, Long startTime, Long endTime, String loadId) {
        // prepare load information
        //TODO
        String url = String.format(urlTemplate, symbol, startTime, endTime);

        // get data from web
        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);
        String[][] result = response.getBody(); //500??? 这个是网页限制的访问么？
        System.out.println(result.length);
        assert result != null;

        // pass a web resources to a list of Trade object
        List<RawKline> rawKlines = Arrays.stream(result)
                .map(part -> new RawKline(part, symbol, loadId))
                .collect(Collectors.toList());

        // load into database
        loadMapper.load(rawKlines);
        transferService.batchTransferAndLoad(rawKlines);
        return loadId;
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