package org.example.service;

import org.example.pojo.Kline;
import org.example.pojo.MoneyType;
import org.example.pojo.RawKline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.LinkPermission;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TransferServiceImplTest {

    @Autowired TransferService transferService;

    @Test
    void batchTransferAndLoad() {
        MoneyType symbol = MoneyType.valueOf("BTC");
        String loadId = "e2501050-5298-4bbf-a8e2-9308aef0449c";
        String[] part= {"1523577600000","7922.99000000","7936.99000000","7919.84000000","7935.61000000","53.68618000","1523577659999","425682.91899601","230","45.80239200","363185.75390966","0"};
        String[] part2 =
        {"1523577660000","7935.54000000","7954.99000000","7930.09000000","7945.67000000","39.59533500","1523577719999","314494.40146720","274","35.04922700","278402.89612295","0"};

        RawKline r1 = new RawKline(part,symbol,loadId);
        RawKline r2 = new RawKline(part2,symbol,loadId);
        List<RawKline> input = new ArrayList<>();
        input.add(r1);
        input.add(r2);
        List<Kline> result =  transferService.batchTransferAndLoad(input);
        for(Kline k: result){
            System.out.println(k);
        }
    }

}