package com.rookie.diting.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Classname SensitiveWordServiceTest
 * @Description TODO
 * @Date 2025/1/10 16:34
 * @Created by liujiapeng
 */
@SpringBootTest
public class SensitiveWordServiceTest {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Test
    public void testReplaceSensitiveWords() {
        String result = sensitiveWordService.replaceSensitiveWords("这是一段包含敏感词做户口本和word2的");
        System.out.println(result);
    }

    @Test
    public void testSensitiveWordServiceList() {
        List<String> result = sensitiveWordService.containsSensitiveWord("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }

}
