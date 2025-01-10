package com.rookie.diting.test.service;

import com.rookie.diting.test.service.SensitiveWordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Classname SensitiveWordServiceTest
 * @Description TODO
 * @Date 2025/1/10 11:02
 * @Created by liujiapeng
 */
@SpringBootTest
public class SensitiveWordServiceTest {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Test
    public void testCheckSensitiveWords() {
        Set<String> sensitiveWords = sensitiveWordService.checkSensitiveWords("This is a test with a badword.");
        assertFalse(sensitiveWords.isEmpty());
        assertTrue(sensitiveWords.contains("badword"));
    }
}
