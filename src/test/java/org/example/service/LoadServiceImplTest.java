package org.example.service;

import org.example.pojo.MoneyType;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoadServiceImplTest {


    @Spy
    private LoadService loadService = new LoadServiceImpl() ;

    @BeforeAll
    void printStart(){
        System.out.println("Start to test ========");

        MockitoAnnotations.openMocks(this);
    }


    //1523577600000/1523664000000
    @Test
    void Load() {
//        LoadService loadService = spy(new LoadServiceImpl());
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449d";
        LoadService ls = spy(loadService);
        System.out.println(loadService);
        String result = loadService.load(MoneyType.valueOf("BTC"),1523577600000L,
                1523664000000L,loadId);
        System.out.println(result);
        //Assertions.assertEquals(loadId,result);
//
//        int result2 = ls.add(12,13);
//        System.out.println(result2);

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