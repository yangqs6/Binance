package org.example.service;

import org.example.pojo.Kline;
import org.example.pojo.RawKline;

import java.util.List;

public interface TransferService {

    List<Kline> batchTransferAndLoad(List<RawKline> rawKlines);

    //删除：
    //List<Kline> batchTransfer(List<RawKline> rawKlines);
}
