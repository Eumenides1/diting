package com.rookie.diting.test.service;

import com.rookie.diting.constants.DesensitizationType;
import com.rookie.diting.constants.ReplacementType;
import com.rookie.diting.domain.SensitiveWordResult;
import com.rookie.diting.utils.DitingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

/**
 * @Classname SensitiveWordServiceTest
 * @Description TODO
 * @Date 2025/1/10 16:34
 * @Created by liujiapeng
 */
@SpringBootTest
public class SensitiveWordServiceTest {


    @Test
    public void testReplaceSensitiveWords() {
        SensitiveWordResult result = DitingUtil.replaceSensitiveWords("这是一段包含敏感词做户口本和word2的", DesensitizationType.PARTIAL_REPLACEMENT);
        System.out.println(result);
    }

    @Test
    public void testSensitiveWordServiceList() {
        SensitiveWordResult result = DitingUtil.containsSensitiveWord("这是一段包含敏感词xi①jinp☁ing和總書記的");
        System.out.println(result);
    }

    @Test
    public void testFindSensitiveWords() {
        SensitiveWordResult result = DitingUtil.getSensitiveWords("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }

}
