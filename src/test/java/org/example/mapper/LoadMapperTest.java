package org.example.mapper;

import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@MybatisTest
@RunWith(SpringRunner.class)
class LoadMapperTest {

    @Autowired
    private LoadMapper loadMapper;

    private static Logger LOG = LoggerFactory.getLogger(LoadMapperTest.class);

    @Test
    void load() {

        String[] part= {"1523577600000","7922.99000000","7936.99000000","7919.84000000","7935.61000000","53.68618000","1523577659999","425682.91899601","230","45.80239200","363185.75390966","0"};
        MoneyType symbol = MoneyType.valueOf("BTC");
        String loadId = String.valueOf(UUID.randomUUID());
        RawKline r1 = new RawKline(part,symbol,loadId);
        List<RawKline> list1 = new ArrayList<>();
        list1.add(r1);
        loadMapper.load(list1);
        LOG.info("insert a raw kline {}",r1);

    }
}