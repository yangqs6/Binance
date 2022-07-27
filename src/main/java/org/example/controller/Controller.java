package org.example.controller;

import org.example.pojo.MoneyType;
import org.example.pojo.exception.InvalidSymbolException;
import org.example.pojo.exception.InvalidTimeException;
import org.example.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


//FROM_UNIXTIME(1156219870)

@RestController
public class Controller {

    @Autowired
    private LoadService loadService;

    //TODO follow load function
    @GetMapping("/load/all/{startTime}/{endTime}")
    public void loadAll(@PathVariable Long startTime, @PathVariable Long endTime) {

        if (endTime < startTime) {
            throw new InvalidTimeException("Invalid Time");
        }
        String loadId = UUID.randomUUID().toString();

        loadService.loadAll(startTime, endTime,loadId);
    }

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
        return loadService.load(m, startTime, endTime, loadId);
    }



}
