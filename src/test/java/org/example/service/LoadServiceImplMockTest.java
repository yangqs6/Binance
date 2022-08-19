package org.example.service;

import org.example.pojo.MoneyType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

//spring test -----
@RunWith(SpringRunner.class)
@SpringBootTest
class LoadServiceImplMockTest {

    @Autowired
    private LoadService loadService;
    //1523577600000/1523664000000
    @Test
    void Load() {
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


}
