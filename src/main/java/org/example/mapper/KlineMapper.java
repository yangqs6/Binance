package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Kline;

import java.util.List;

@Mapper
public interface KlineMapper {






     @Select("SELECT load_id as loadId, symbol, open_time as openTime, open_ as open, high, low, close_ as close, volume,close_time as closeTime" +
             " FROM kline WHERE load_id=#{loadId} and symbol=#{symbol} " +
             "and open_time between #{startTime} and #{endTime} " +
             "and close_time between #{startTime} and #{endTime};")
     List<Kline> getKline(String loadId, String symbol, String startTime, String endTime);
}
