package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;

import java.util.List;

public interface LoadService {

    void batchLoad(List<RawKline> rawKlines);

    String load(MoneyType m, Long startTime, Long endTime, String loadId);

//    void load(MoneyType symbol, Long startTime, Long endTime);
//
    void loadAll(Long startTime, Long endTime, String loadId);

    void loadAllAsync(Long startTime, Long endTime, String loadId);

    //TODO
    List<Kline> loadRedis(String symbol, Long startTime, Long endTime, String loadId, int frequency) throws JsonProcessingException;


}
