package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.mapper.KlineMapper;
import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.exception.InvalidSymbolException;
import org.example.pojo.exception.InvalidTimeException;
import org.example.service.CalculateServiceImpl;
import org.example.service.LoadService;
import org.example.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CalculatorController {

    @Autowired
    private KlineMapper klineMapper;

    @Autowired
    private CalculateServiceImpl calculateServiceImpl;

    @Autowired
    private LoadService loadService;

    @Autowired
    private RedisService redisService;

    //http://localhost:8082/getkline/BTC/1523577600000/1523664000000/31a42bc7-0791-4b31-9cff-6ba6e88ff94c

    @GetMapping("/getkline/{symbol}/{startTime}/{endTime}/{loadId}")
    public List<Kline> getKline(@PathVariable String symbol,@PathVariable Long startTime, @PathVariable Long endTime,@PathVariable String loadId){

        Date date1 = new Date(endTime);
        SimpleDateFormat jdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf1.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String closeTime = jdf1.format(date1);

        Date date2 = new Date(startTime);
        SimpleDateFormat jdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf2.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String beginTime = jdf1.format(date2);

        return klineMapper.getKline(loadId,symbol,beginTime,closeTime);
    }

    //http://localhost:8082/calculate/BTC/1523577600000/1523664000000/31a42bc7-0791-4b31-9cff-6ba6e88ff94c/5
    //@GetMapping("/load/{symbol}/{startTime}/{endTime}")
    @GetMapping("/calculate/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}")
    public List<Kline> testCalculate(@PathVariable String symbol, @PathVariable Long startTime, @PathVariable Long endTime,@PathVariable String loadId,@PathVariable int frequency){
          //return "hello";
        MoneyType m;
        try {
            m = MoneyType.valueOf(symbol);
        } catch (IllegalArgumentException e) {
            throw new InvalidSymbolException("No such enum constant and you are supposed to choose among BTC, ETH, LTC ");
        }
        if (endTime < startTime) {
            throw new InvalidTimeException(String.format("Invalid Time, endTime must greater than startTime, endTime=%s, startTime=%s", endTime, startTime));
        }

        Date date1 = new Date(endTime);
        SimpleDateFormat jdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf1.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String closeTime = jdf1.format(date1);

        Date date2 = new Date(startTime);
        SimpleDateFormat jdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf2.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String beginTime = jdf1.format(date2);
        List<Kline> klines = klineMapper.getKline(loadId,symbol,beginTime,closeTime);
        System.out.println(klines.size());
        List<Kline> result = calculateServiceImpl.calculate(klines,symbol,loadId,frequency);
        return result;
    }



//    void loadCalculate(List<Kline> klines, String symbol, String loadId, Long startTime, Long endTime, int frequency) throws JsonProcessingException;
//    List<Kline> fetchCalculate(String symbol, String loadId, Long startTime, Long endTime, int frequency) throws JsonProcessingException;
//

    //http://localhost:8082/load/BTC/1523577600000/1523664000000/

    //http://localhost:8082/redis/BTC/1523577600000/1523664000000/e2501050-5298-4bbf-a8e2-9308aef0449c/5
    //@GetMapping("/redis/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}")
    @GetMapping("/redis/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}")
    //public void RedisCalculate1(@PathVariable String symbol, @PathVariable Long startTime, @PathVariable Long endTime,@PathVariable String loadId,@PathVariable int frequency) throws JsonProcessingException {
    public void RedisCalculate1(@PathVariable String symbol,@PathVariable Long startTime, @PathVariable Long endTime,@PathVariable String loadId,@PathVariable int frequency) throws JsonProcessingException {

        Date date1 = new Date(endTime);
        SimpleDateFormat jdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf1.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String closeTime = jdf1.format(date1);

        Date date2 = new Date(startTime);
        SimpleDateFormat jdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf2.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String beginTime = jdf1.format(date2);
        List<Kline> klines = klineMapper.getKline(loadId,symbol,beginTime,closeTime);
        redisService.loadCalculate(klines,symbol,loadId,startTime,endTime,frequency);
//        List<Kline> result = redisService.fetchCalculate(symbol,loadId,startTime,endTime,frequency);
//        for(Kline k:result){
//            System.out.println(k);
//        }
    }



    //public void loadRedis(MoneyType m, Long startTime, Long endTime, String loadId, int frequency) throws JsonProcessingException {
    //http://localhost:8082/redisLoad/BTC/1523577600000/1523664000000/e2501050-5298-4bbf-a8e2-9308aef0449c/5
//    @GetMapping("/redisLoad/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}")
//    public List<Kline> RedisLoad(@PathVariable String symbol,@PathVariable Long startTime, @PathVariable Long endTime, @PathVariable String loadId, @PathVariable int frequency) throws JsonProcessingException {
//        List<Kline> result = loadService.loadRedis(symbol,startTime,endTime,loadId,frequency);
//        return result;
//    }
}





