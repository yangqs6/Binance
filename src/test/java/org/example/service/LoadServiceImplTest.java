package org.example.service;

import org.example.mapper.LoadMapper;
import org.example.pojo.MoneyType;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoadServiceImplTest {


//    @Spy
//    private LoadService loadService = new LoadServiceImpl() ;

    //@SpringBootApplication(scanBasePackages = "org.example.")

    @Autowired
    private LoadServiceImpl loadService;

    @MockBean
    @InjectMocks
    private LoadMapper loadMapper;
//
//    @MockBean
//    private TransferService transferService;
//
    @MockBean
    @InjectMocks
    private RestTemplate restTemplate;

//    @MockBean
//    private RedisService redisService;

//    @MockBean
//    private CalculateService calculateService;

    @BeforeAll
    public void setUp(){
        System.out.println("Start to test ===========");
        String url= "https://www.binance.com/api/v3/klines?symbol=%sUSDT&interval=1m&startTime=%s&endTime=%s";
////
//        loadService.restTemplate = restTemplate;
        String[][] body = {{"1523577600000","7922.99000000","7936.99000000","7919.84000000","7935.61000000","53.68618000","1523577659999","425682.91899601","230","45.80239200","363185.75390966","0"}};
        ResponseEntity<String[][]> response = new ResponseEntity<>(body,HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(url,String[][].class)).thenReturn(response);

        //ReflectionTestUtils.setField(LoadServiceImpl.class,"number", 123);
        //ReflectionTestUtils.setField(LoadServiceImpl.class,"urlTemplate", url);
        //loadService.urlTemplate = url;

    }




    //1523577600000/1523664000000
    @Test
    void Load() {

        String loadId = "e2501050-5298-4bbf-a8e2-9309aef0449d";
        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;
        MoneyType m = MoneyType.valueOf("BTC");
        String result = loadService.load(m,startTime,
                endTime,loadId);
//        //Mockito.verify(loadService).load(m,startTime,endTime,loadId);
        Assertions.assertEquals(loadId,result);

//        System.out.println(loadService.urlTemplate);
//        System.out.println("hi");
    }

    @Test
    void loadAll() {
    }

    @Test
    void loadAllAsync() {
    }

    @Test
    void batchLoad() {
    }

    @Test
    void loadRedis() {
    }


    @AfterAll
    void printEnd(){
        System.out.println("End to test ========");
    }
}