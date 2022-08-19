package org.example.mapper;

import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@RunWith(SpringRunner.class)
class KlineMapperTest {

    @Autowired
    private KlineMapper klineMapper;

    private static Logger LOG = LoggerFactory.getLogger(LoadMapperTest.class);

    @Test
    void getKline() {

        String symbol = "BTC";
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449c";

        List<Kline> result = klineMapper.getKline(loadId,symbol,"2018-04-12 10:45:00","2018-04-12 10:45:59");
        LOG.info("{}",result);
    }
}