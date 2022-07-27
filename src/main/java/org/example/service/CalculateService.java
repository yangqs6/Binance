package org.example.service;

import org.example.mapper.KlineMapper;
import org.example.pojo.Kline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateService {


    @Autowired
    private KlineMapper klineMapper;

    public List<Kline>  calculate(List<Kline> klines, String symbol, String loadId, int frequency){

        int i = 0;
        List<Kline> result = new ArrayList<>();
        while(i < klines.size()){
            Kline k = new Kline();
            k.setSymbol(symbol);
            k.setLoadId(loadId);
            k.setOpenTime(klines.get(i).getOpenTime());
            k.setCloseTime(klines.get(i+frequency-1).getCloseTime());
            k.setOpen(klines.get(i).getOpen());
            k.setClose(klines.get(i+frequency-1).getClose());

            double maxValue = klines.get(i).getHigh();
            double minValue = klines.get(i).getLow();
            double total = 0;

            for(int j = 0; j< frequency;j++){
                total += klines.get(i+j).getVolume();
                double tempHigh = klines.get(i+j).getHigh();
                double tempLow = klines.get(i+j).getLow();
                if(tempHigh>maxValue){
                    maxValue = tempHigh;
                }
                if(tempLow<tempLow){
                    minValue = tempLow;
                }
            }
            k.setHigh(maxValue);
            k.setLow(minValue);

            NumberFormat nf = NumberFormat.getNumberInstance();
             // 保留两位小数
            nf.setMaximumFractionDigits(6);

            k.setVolume(Double.valueOf(nf.format(total)));
            result.add(k);
            i += frequency;
        }

        return result;
    }


}
