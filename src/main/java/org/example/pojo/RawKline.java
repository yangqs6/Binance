package org.example.pojo;
// TODO: use lombok
//@Getter
//@Setter

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//TODO remove getter setter for each property
//@Getter
//@Setter
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
// KlineRawData
public class RawKline {

    private String symbol;
    private String open;// TODO 不要_
    private Long openTime;
    private Long closeTime;
    private String high;
    private String low;
    private String close;
    private String volume;
    private String quoteAssetVolume;
    private int numOfTrades;
    private String buyBaseVolume;
    private String buyQuoteVolume;
    private String ignore;
    private String loadId; //TODO uuid

    //load id + symbol + start time + end time --> 4 columns


    @Override
    public String toString() {
        return "Trade{" +
                "symbol='" + symbol + '\'' +
                ", openTime=" + openTime +
                ", open1='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close1='" + close + '\'' +
                ", volume='" + volume + '\'' +
                ", closeTime=" + closeTime +
                ", quoteAssetVolume='" + quoteAssetVolume + '\'' +
                ", numOfTrades=" + numOfTrades +
                ", buyBaseVolume='" + buyBaseVolume + '\'' +
                ", buyQuoteVolume='" + buyQuoteVolume + '\'' +
                ", ignore='" + ignore + '\'' +
                '}';
    }

    public Long getOpen_Long() {
        return openTime;
    }

    public void setOpen_Long(Long open_Long) {
        this.openTime = open_Long;
    }

    public Long getClose_Long() {
        return closeTime;
    }

    public void setClose_Long(Long close_Long) {
        this.closeTime = close_Long;
    }


    public RawKline(String[] part, MoneyType symbol, String loadId){
        this.symbol = symbol.toString();
        this.openTime = Long.valueOf(part[0]);

        //System.out.println("\n"+openTime+"\n");

        this.open = part[1];
        this.high = part[2];
        this.low = part[3];
        this.close = part[4];
        this.volume = part[5];

        this.closeTime = Long.valueOf(part[6]);
        this.quoteAssetVolume = part[7];
        this.numOfTrades = Integer.parseInt(part[8]);
        this.buyBaseVolume = part[9];
        this.buyQuoteVolume = part[10];
        this.ignore = part[11];
        this.loadId = loadId;
    }
}

