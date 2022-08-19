package org.example.service;

import org.example.pojo.Kline;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CalculateServiceImplTest {

    @Autowired CalculateService calculateService;

    @Test
    void calculate() {
        String id = "e2501050-5298-4bbf-a8e2-9308aef0449c";
        String s = "BTC";
        Kline k = new Kline( id,s,"2018-04-12 08:00:00",7922.99,7936.99,7919.84,7935.61,53.68618,"2018-04-12 08:00:59");
        Kline k1 = new Kline(id,s,"2018-04-12 08:01:00",7935.54,7954.99,7930.09,7945.67,39.595335,"2018-04-12 08:01:59");
        Kline k2 = new Kline(id,s,"2018-04-12 08:02:00",7950.0,7954.98, 7946.0, 7948.0,28.717295,"2018-04-12 08:02:59");
        Kline k3 = new Kline(id,s,"2018-04-12 08:03:00",7950.26,7959.72, 7950.0, 7957.0,56.889722,"2018-04-12 08:03:59");
        Kline k4 = new Kline(id,s,"2018-04-12 08:04:00",7957.0, 7979.0,7942.35,7978.89,75.47576,"2018-04-12 08:04:59");

        List<Kline> klines = new ArrayList<>();
        klines.add(k);
        klines.add(k1);
        klines.add(k2);
        klines.add(k3);
        klines.add(k4);
        int frequency = 5;
        List<Kline> result = calculateService.calculate(klines,s,id,frequency);
        for(Kline i : result){
            System.out.println(i);
        }

        assertEquals(id,result.get(0).getLoadId());
        assertEquals(s,result.get(0).getSymbol());
        assertEquals("2018-04-12 08:00:00",result.get(0).getOpenTime());
        assertEquals("2018-04-12 08:04:59",result.get(0).getCloseTime());
        assertEquals(7922.99,result.get(0).getOpen());
        assertEquals(7978.89,result.get(0).getClose());

    }

    @Test
    void getKline() {
        String s = "BTC";
        //1523577600000/1523664000000
        Long stTime =1523577600000L;
        Long edTime =1523577719999L;
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449c";
        List<Kline> result = calculateService.getKline(s,stTime,edTime,loadId);
        for(Kline k : result){
            System.out.println(k);
        }

        assertEquals(loadId,result.get(0).getLoadId());
        assertEquals(s,result.get(0).getSymbol());
        assertEquals("2018-04-12 08:00:00",result.get(0).getOpenTime());
        assertEquals("2018-04-12 08:00:59",result.get(0).getCloseTime());
        assertEquals(7922.99,result.get(0).getOpen());
        assertEquals(7935.61,result.get(0).getClose());
        assertEquals(7936.99,result.get(0).getHigh());
        assertEquals(7919.84,result.get(0).getLow());


        assertEquals(loadId,result.get(1).getLoadId());
        assertEquals(s,result.get(1).getSymbol());
        assertEquals("2018-04-12 08:01:00",result.get(1).getOpenTime());
        assertEquals("2018-04-12 08:01:59",result.get(1).getCloseTime());
        assertEquals(7935.54,result.get(1).getOpen());
        assertEquals(7945.67,result.get(1).getClose());
        assertEquals(7954.99,result.get(1).getHigh());
        assertEquals(7930.09,result.get(1).getLow());



    }
}