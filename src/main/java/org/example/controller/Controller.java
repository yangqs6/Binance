package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.exception.InvalidSymbolException;
import org.example.pojo.exception.InvalidTimeException;
import org.example.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//FROM_UNIXTIME(1156219870)

@RestController
public class Controller {

    @Autowired
    private LoadService loadService;

    //TODO follow load function
    //http://localhost:8082/load/all/1523577600000/1523664000000
    @GetMapping("/load/all/{startTime}/{endTime}")
    public void loadAll(@PathVariable Long startTime, @PathVariable Long endTime) {

        long startPTime = new Date().getTime();
        if (endTime < startTime) {
            throw new InvalidTimeException("Invalid Time");
        }
        String loadId = UUID.randomUUID().toString();

        loadService.loadAll(startTime, endTime,loadId);

        long endPTime = new Date().getTime();
        System.out.println("本程序运行 " + (endPTime - startPTime)
                + " 毫秒完成。");

    }

    //http://localhost:8082/load/BTC/1523577600000/1523664000000
    @GetMapping("/load/{symbol}/{startTime}/{endTime}")
    public String load(@PathVariable String symbol, @PathVariable Long startTime, @PathVariable Long endTime, @RequestParam(value = "load_id", defaultValue = "") String loadId) {
        MoneyType m;
        try {
            m = MoneyType.valueOf(symbol);
        } catch (IllegalArgumentException e) {
            throw new InvalidSymbolException("No such enum constant and you are supposed to choose among BTC, ETH, LTC ");
        }
        if (endTime < startTime) {
            throw new InvalidTimeException(String.format("Invalid Time, endTime must greater than startTime, endTime=%s, startTime=%s", endTime, startTime));
        }
        loadId = loadId.isEmpty()?UUID.randomUUID().toString():loadId;

        String r = loadService.load(m, startTime, endTime, loadId);
        System.out.println(r);
        return r;
    }

    //http://localhost:8082/getkline/BTC/1523577600000/152366400000/
    //http://localhost:8082/loadasyn/1523577600000/152366400000/
    @GetMapping("/loadasyn/{startTime}/{endTime}")
    public void loadAsyn(@PathVariable Long startTime,@PathVariable Long endTime,@RequestParam(value = "load_id", defaultValue = "") String loadId){

        long startPTime = new Date().getTime();
        if (endTime < startTime) {
            throw new InvalidTimeException(String.format("Invalid Time, endTime must greater than startTime, endTime=%s, startTime=%s", endTime, startTime));
        }
        loadId = loadId.isEmpty()?UUID.randomUUID().toString():loadId;
        loadService.loadAllAsync(startTime,endTime,loadId);

        long endPTime = new Date().getTime();
        System.out.println("本程序运行 " + (endPTime - startPTime)
                + " 毫秒完成。");

    }


    @GetMapping("/redisLoad/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}")
    public List<Kline> RedisLoad(@PathVariable String symbol, @PathVariable Long startTime, @PathVariable Long endTime, @PathVariable String loadId, @PathVariable int frequency) throws JsonProcessingException {
        List<Kline> result = loadService.loadRedis(symbol,startTime,endTime,loadId,frequency);
        return result;
    }



}
