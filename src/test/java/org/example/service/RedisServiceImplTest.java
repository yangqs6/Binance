package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.pojo.Kline;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class RedisServiceImplTest {

    @Autowired
    private RedisService redisService;

    @Test
    void loadCalculateAndFetch() throws JsonProcessingException {

        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;
        int frequency = 5;
        String openTime = "2018-04-12 10:45:00";
        Double open = 7922.99000000;
        Double high = 7936.99000000;
        Double low = 7919.84000000;
        Double close = 7935.61000000;
        Double volume = 53.68618000;
        String closeTime = "2018-04-12 10:45:59";
        String symbol = "BTC";
        String loadId = String.valueOf(UUID.randomUUID());
        Kline r1 = new Kline(loadId,symbol,openTime,open,high,low,close,volume,closeTime);
        List<Kline> list1 = new ArrayList<>();
        list1.add(r1);
        redisService.loadCalculate(list1,symbol,loadId,startTime,endTime,frequency);
        List<Kline> result = redisService.fetchCalculate(symbol,loadId,startTime,endTime,frequency);
        System.out.println(result);
        assertEquals(list1.get(0).getLoadId(),result.get(0).getLoadId());
        assertEquals(list1.get(0).getClose(),result.get(0).getClose());
        assertEquals(list1.get(0).getCloseTime(),result.get(0).getCloseTime());
        assertEquals(list1.get(0).getHigh(),result.get(0).getHigh());
        assertEquals(list1.get(0).getLow(),result.get(0).getLow());
        assertEquals(list1.get(0).getSymbol(),result.get(0).getSymbol());
        assertEquals(list1.get(0).getOpenTime(),result.get(0).getOpenTime());
        assertEquals(list1.get(0).getVolume(),result.get(0).getVolume());
    }

    @Test
    void exists() {
        String symbol = "BTC";
        String loadId = String.valueOf(UUID.randomUUID());
        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;
        int frequency = 5;
        boolean result = redisService.exists(symbol,loadId,startTime,endTime,frequency);
        assertFalse(result);
    }

}