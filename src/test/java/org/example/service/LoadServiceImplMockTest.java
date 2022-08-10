package org.example.service;

import org.example.pojo.MoneyType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class LoadServiceImplMockTest {

    @Mock
    private LoadService loadService;

    //@BeforeAll
//    void setUp(){
//        System.out.println("Start to test ========");
//        MockitoAnnotations.openMocks(this);
//    }




    //1523577600000/1523664000000
    @Test
    void Load() {
        MockitoAnnotations.openMocks(this);
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449d";
//        Assertions.assertEquals(loadService.load(MoneyType.valueOf("BTC"),1523577600000L,
//                1523664000000L,loadId),loadId);
        Mockito.when(loadService.load(MoneyType.valueOf("BTC"),
                1523577600000L,1523577600000L,loadId)).thenReturn("0");
        Assertions.assertEquals("0",loadService.load(MoneyType.valueOf("BTC"),
                1523577600000L,1523577600000L,loadId));


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


//    @AfterAll
//    void printEnd(){
//        System.out.println("End to test ========");
//    }
}