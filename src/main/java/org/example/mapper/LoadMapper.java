package org.example.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.RawKline;

import java.util.List;

@Mapper
public interface LoadMapper {





    @Insert("<script> " +
            "INSERT INTO bank(symbol,open_time,open_,high,low,close_,volume,close_time,quote_asset_volume,num_of_trades,buy_base_volume,buy_quote_volume,ignore_,load_id) values" +
            "<foreach collection ='list' item='record' separator=','>" +
            "(#{record.symbol},#{record.openTime},#{record.open},#{record.high},#{record.low},#{record.close},#{record.volume},#{record.closeTime},#{record.quoteAssetVolume},#{record.numOfTrades},#{record.buyBaseVolume},#{record.buyQuoteVolume},#{record.ignore},#{record.loadId})" +
            "</foreach>" +
            "</script>")
    void load(List<RawKline> list);



    //void load(List<Trade> trades);
}
//    @Insert("<script> INSERT INTO chat_record_table "
//            + "(id,send_user_id) "
//            + "VALUES "
//            + "<foreach collection = 'list' item='record' separator=',' > "
//            + " (#{record.id},#{record.sendUserId}) "
//            + "</foreach>"
//            + "</script>")
//    Integer batchInsert(List<ChatRecordDO> list)throws Exception;





