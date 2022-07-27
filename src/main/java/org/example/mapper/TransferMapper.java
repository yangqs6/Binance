package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Kline;

import java.util.List;

@Mapper
public interface TransferMapper {


    //https://www.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1m&startTime=1523577600000&endTime=1523664000000
    //http://localhost:8081/load/BTC/1523577600000/1523664000000
    @Insert("<script> " +
            "INSERT INTO kline(symbol,open_time,open_,high,low,close_,volume,close_time,load_id) values" +
            "<foreach collection ='list' item='record' separator=','>" +
            "(#{record.symbol},#{record.openTime},#{record.open},#{record.high},#{record.low},#{record.close},#{record.volume},#{record.closeTime},#{record.loadId})" +
            "</foreach>" +
            "</script>")
    void loadkline(List<Kline> list);
}
