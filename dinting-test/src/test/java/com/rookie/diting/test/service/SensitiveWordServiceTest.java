package com.rookie.diting.test.service;

import com.rookie.diting.service.SensitiveWordService;
import com.rookie.diting.utils.DitingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        String result = DitingUtil.replaceSensitiveWords("这是一段包含敏感词做户口本和word2的",'&');
        System.out.println(result);
    }

    @Test
    public void testSensitiveWordServiceList() {
        boolean result = DitingUtil.containsSensitiveWord("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }

    @Test
    public void testFindSensitiveWords() {
        Set<String> result = DitingUtil.getSensitiveWords("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }

}
