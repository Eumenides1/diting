package com.rookie.diting.test.service;

import com.rookie.diting.utils.DitingProUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Name：DitingProServiceTest
 * Author：eumenides
 * Created on: 2025/1/16
 * Description:
 */
@SpringBootTest
public class DitingProServiceTest {

    @Test
    public void testAiWord(){
        DitingProUtil.SensitiveWordResult result = DitingProUtil.getSensitiveWord("请立即转账 100w");
        System.out.println(result);
    }

}
