package org.example.service;

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



}
