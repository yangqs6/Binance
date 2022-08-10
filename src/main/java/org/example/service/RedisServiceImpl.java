package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pojo.Kline;
import org.example.pojo.KlineList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceImpl implements RedisService{

    //把数据存放在redis里
    //存放的数据就是List<Kline>


    //User user = new User("胡歌",18);
    //        //手动序列化
    //        String json = mapper.writeValueAsString(user);
    //        //写入一条数据到redis
    //        stringRedisTemplate.opsForValue().set("user:200",json);
    //        //读取数据
    //        String val = stringRedisTemplate.opsForValue().get("user:200");
    //        //反序列化
    //        User user1 = mapper.readValue(val,User.class);
    //        System.out.println("user1 = "+user1);

    //查询的时候用啥查？ symbol loadid starttime endtime frequency


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    public void loadCalculate(List<Kline> klines, String symbol, String loadId,Long startTime, Long endTime, int frequency) throws JsonProcessingException {

            //String json = mapper.writeValueAsString(new KlineList(klines));
            String json = mapper.writeValueAsString(klines);
            String key = String.format("%s:%s:%s:%s:%s",symbol,loadId,startTime.toString(),endTime.toString(),frequency+"");
            stringRedisTemplate.opsForValue().set(key,json,1800, TimeUnit.SECONDS);

    }



    public List<Kline> fetchCalculate(String symbol, String loadId, Long startTime, Long endTime, int frequency) {

        String key = getKey(symbol,loadId,startTime,endTime,frequency);
        //String val = stringRedisTemplate.opsForValue().get("user:200");
        System.out.println(key);

        System.out.println("=========================================================");
        String val = stringRedisTemplate.opsForValue().get(key);
        System.out.println(val);
        System.out.println("=========================================================");
        //KlineList klineList = null;
        Kline[] klineList = null;
        try {
            klineList = mapper.readValue(val, Kline[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("cannot get val %s", val),e);
        }
        //return klineList.getKlineList();
        return Arrays.asList(klineList);
    }

    public Boolean exists(String symbol, String loadId, Long startTime, Long endTime, int frequency){
        String key = getKey(symbol,loadId,startTime,endTime,frequency);
        return stringRedisTemplate.hasKey(key);
    }


    private String getKey(String symbol, String loadId, Long startTime, Long endTime, int frequency) {
        return String.format("%s:%s:%s:%s:%s",symbol,loadId,startTime.toString(),endTime.toString(),frequency+"");

    }

    //TODO mock/spy in java unit test


}
