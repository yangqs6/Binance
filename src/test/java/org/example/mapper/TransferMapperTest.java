package org.example.mapper;

import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@RunWith(SpringRunner.class)
class TransferMapperTest {


    @Autowired
    private TransferMapper transferMapper;

    private static Logger LOG = LoggerFactory.getLogger(TransferMapperTest.class);

    @Test
    void loadkline() {


//        private String loadId;
//        private String symbol;
//        // TODO object type
//        private String openTime;
//        private Double open;// TODO
//        private Double high;
//        private Double low;
//        private Double close;
//        private Double volume;
//        private String closeTime;
        String openTime = "2018-04-12 10:45:00";
        Double open = 7922.99000000;
        Double high = 7936.99000000;
        Double low = 7919.84000000;
        Double close = 7935.61000000;
        Double volume = 53.68618000;
        String closeTime = "2018-04-12 10:45:59";

        //String[] part= {"1523577600000","7922.99000000","7936.99000000","7919.84000000","7935.61000000","53.68618000","1523577659999","425682.91899601","230","45.80239200","363185.75390966","0"};
        String symbol = "BTC";
        String loadId = String.valueOf(UUID.randomUUID());
        Kline r1 = new Kline(loadId,symbol,openTime,open,high,low,close,volume,closeTime);
        List<Kline> list1 = new ArrayList<>();
        list1.add(r1);
        transferMapper.loadkline(list1);
        //LOG.info("load kline to the database {}",list1);
    }
}