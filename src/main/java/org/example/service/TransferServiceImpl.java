package org.example.service;

import org.example.mapper.TransferMapper;
import org.example.pojo.Kline;
import org.example.pojo.RawKline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransferServiceImpl implements TransferService{

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public List<Kline> batchTransferAndLoad(List<RawKline> rawKlines) {

        List<Kline> result = rawKlines.stream()
                .map(Kline::new)
                .collect(Collectors.toList());
        transferMapper.loadkline(result);
        return result;
    }

    public List<Kline> batchTransfer(List<RawKline> rawKlines){
        List<Kline> result = rawKlines.stream()
                .map(Kline::new)
                .collect(Collectors.toList());
        return result;
    }


}
