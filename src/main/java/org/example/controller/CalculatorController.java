package org.example.controller;

import org.example.mapper.KlineMapper;
import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.example.pojo.exception.InvalidSymbolException;
import org.example.pojo.exception.InvalidTimeException;
import org.example.service.CalculateService;
import org.example.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CalculatorController {

    // 500 ?




    @Autowired
    private KlineMapper klineMapper;

    @Autowired
    private CalculateService calculateService;


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
        List<Kline> result = calculateService.calculate(klines,symbol,loadId,frequency);
        return result;
    }
}
