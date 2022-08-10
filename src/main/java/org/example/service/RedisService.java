package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.pojo.Kline;

import java.util.List;

public interface RedisService {


    void loadCalculate(List<Kline> klines, String symbol, String loadId, Long startTime, Long endTime, int frequency) throws JsonProcessingException;
    List<Kline> fetchCalculate(String symbol, String loadId, Long startTime, Long endTime, int frequency);
    Boolean exists(String symbol, String loadId, Long startTime, Long endTime, int frequency);

}
