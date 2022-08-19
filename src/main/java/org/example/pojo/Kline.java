package org.example.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Supplier;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor

public class Kline {
//public class Kline implements Supplier<String> {

    private String loadId;
    private String symbol;
    // TODO object type
    private String openTime;
    private Double open;// TODO
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private String closeTime;


    // 为啥要用包装类？？？


    public Kline(String loadId, String symbol, String openTime, Double open, Double high, Double low, Double close, Double volume, String closeTime) {
        this.loadId = loadId;
        this.symbol = symbol;
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
    }


    public Kline(RawKline rawKline){
        this.loadId = rawKline.getLoadId();
        this.symbol = rawKline.getSymbol();
        Long openLong = rawKline.getOpenTime();

        Date date = new Date(openLong);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        this.openTime = jdf.format(date);


        this.open = Double.parseDouble(rawKline.getOpen());
        this.high = Double.parseDouble(rawKline.getHigh());
        this.low = Double.parseDouble(rawKline.getLow());
        this.close = Double.parseDouble(rawKline.getClose());
        this.volume = Double.parseDouble(rawKline.getVolume());
        Long closeLong = rawKline.getCloseTime();

        Date date1 = new Date(closeLong);
        SimpleDateFormat jdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf1.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        this.closeTime = jdf1.format(date1);

    }

//    @Override
//    public String toString() {
//        return "Kline{" +
//
//                ", openTime=" + openTime +
//                ", open=" + open +
//                ", high=" + high +
//                ", low=" + low +
//                ", close=" + close +
//                ", volume=" + volume +
//                '}';
//    }


    @Override
    public String toString() {
        return "Kline{" +
                "loadId='" + loadId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", openTime='" + openTime + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }
}

