package org.example.service;

import org.example.pojo.Kline;

import java.util.ArrayList;
import java.util.List;

public interface CalculateService {
    List<Kline> calculate(List<Kline> klines, String symbol, String loadId, int frequency);
    List<Kline> getKline(String symbol, Long startTime, Long endTime, String loadId);



    }
