package org.example.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import org.example.pojo.MoneyType;
import org.example.pojo.exception.InvalidTimeException;
import org.example.service.LoadService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.UUID;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.ServerResponse.status;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoadService loadService;

    @Test
    void loadAll() throws Exception {
        //Mockito.when(loadService.loadAll()).then()
        String url = "/load/all/{startTime}/{endTime}";
        mvc.perform(MockMvcRequestBuilders.get(url,"1523577600000","1523664000000"))
                .andDo(print())
                .andReturn();

    }

//
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
//    }


//    mockMvc.perform(request)
//            .andExpect(status().isOk())//返回HTTP状态为200
//            .andExpect(jsonPath("$.status", not("E")))//使用jsonPath解析JSON返回值，判断具体的内容, 此处不希望status返回E
//            .andExpect(content().string(containsString("选择浏览器打开即可")))//返回值为字符串，字符串包含比较，也可以字符串相等等比较，content()表示返回的结果
//            .andDo(print());//打印结果


    @Test
    void load() throws Exception {
        //String loadId = UUID.randomUUID().toString();
        MoneyType m = MoneyType.valueOf("BTC");
        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;

        String loadId = UUID.randomUUID().toString();

        String url = "http://localhost:8082/load/{m}/{startTime}/{endTime}?load_id={loadId}";

        String expect = loadId;
        when(loadService.load(m,startTime,endTime,loadId)).thenReturn(loadId);

        //String resultString = mvc.perform(MockMvcRequestBuilders.get(url,m,startTime,endTime,loadId))
        String resultString = mvc.perform(MockMvcRequestBuilders.get(url,m,startTime,endTime,loadId))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultString);
        assertEquals(resultString,expect);


    }

    @Test
    void loadAsyn() throws Exception {
        String loadId = UUID.randomUUID().toString();
        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;
        String url = "/loadasyn/{startTime}/{endTime}?load_id={loadId}";
        mvc.perform(MockMvcRequestBuilders.get(url,startTime,endTime,loadId))
                .andDo(print())
                .andReturn();
    }

    @Test
    void loadNoSuchNum() throws Exception {
        String loadId = UUID.randomUUID().toString();
        Long startTime = 1523577600000L;
        Long endTime = 1523664000000L;
        String m1 = "abc";
        MoneyType m = MoneyType.valueOf("BTC");
        String url = "http://localhost:8082/load/{m}/{startTime}/{endTime}?load_id={loadId}";
        String expect = loadId;
        try{
            when(loadService.load(m,startTime,endTime,loadId)).thenReturn(loadId);
            //String resultString = mvc.perform(MockMvcRequestBuilders.get(url,m,startTime,endTime,loadId))
            String resultString = mvc.perform(MockMvcRequestBuilders.get(url,m1,startTime,endTime,loadId))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            Assertions.assertTrue(e instanceof NestedServletException);
        }
    }


    @Test
    void loadWrongTime() throws Exception {
        MoneyType m = MoneyType.valueOf("BTC");
        Long endTime = 1523577600000L;
        Long startTime = 1523664000000L;
        String loadId = UUID.randomUUID().toString();
        String url = "http://localhost:8082/load/{m}/{startTime}/{endTime}?load_id={loadId}";

        String expect = loadId;
        try{
            when(loadService.load(m,startTime,endTime,loadId)).thenReturn(loadId);
            String resultString = mvc.perform(MockMvcRequestBuilders.get(url,m,startTime,endTime,loadId))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
        }catch(Exception e) {
            System.out.println(e.getClass());
            Assertions.assertTrue(e instanceof NestedServletException);
        }

    }

    @Test
    void loadAsynWrongTime() throws Exception {
        String loadId = UUID.randomUUID().toString();
        Long endTime = 1523577600000L;
        Long startTime = 1523664000000L;
        String url = "/loadasyn/{startTime}/{endTime}?load_id={loadId}";
        try{
            mvc.perform(MockMvcRequestBuilders.get(url,startTime,endTime,loadId))
                    .andDo(print())
                    .andReturn();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            //class org.springframework.web.util.NestedServletException
            Assertions.assertTrue(e instanceof NestedServletException);
        }
    }



    @Test
    void redisLoad() {
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449c";
        String symbol = "BTC";
        Long endTime = 1523577600000L;
        Long startTime = 1523664000000L;
        int frequency = 5;
        String url = "/redisLoad/{symbol}/{startTime}/{endTime}/{loadId}/{frequency}";

        try{
            mvc.perform(MockMvcRequestBuilders.get(url,symbol,startTime,endTime,loadId,frequency))
                    .andDo(print())
                    .andReturn();
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }

    }









}