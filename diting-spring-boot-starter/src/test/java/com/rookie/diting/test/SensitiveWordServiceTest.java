package com.rookie.diting.test;

import com.rookie.diting.context.SensitiveWordContext;
import com.rookie.diting.loader.SensitiveWordLoader;
import com.rookie.diting.service.SensitiveWordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Name：SensitiveWordServiceTest
 * Author：eumenides
 * Created on: 2025/1/11
 * Description:
 */
public class SensitiveWordServiceTest {
    private SensitiveWordContext sensitiveWordContext;
    private SensitiveWordService sensitiveWordService;
    @BeforeEach
    void setUp() throws Exception {
        // 模拟敏感词加载器
        SensitiveWordLoader loader = mock(SensitiveWordLoader.class);
        when(loader.loadSensitiveWords()).thenReturn(Set.of("word1", "word2"));

         // 初始化上下文
        sensitiveWordContext = new SensitiveWordContext(loader);
        sensitiveWordContext.init();

        // 初始化服务
        sensitiveWordService = new SensitiveWordService(sensitiveWordContext);
    }
    @Test
    void testContainsSensitiveWord() {
        String text1 = "这是一段包含敏感词word1和word2的文本。";
        String text2 = "这是一段正常的文本。";

        assertTrue(sensitiveWordService.containsSensitiveWord(text1));
        assertFalse(sensitiveWordService.containsSensitiveWord(text2));
    }

    @Test
    void testReplaceSensitiveWords() {
        String text = "这是一段包含敏感词word1和word2的文本。";
        String expected = "这是一段包含敏感词*****和*****的文本。";
        String actual = sensitiveWordService.replaceSensitiveWords(text, '*');
        assertEquals(expected, actual);
    }

    @Test
    void testFindSensitiveWords() {
        String text = "这是一段包含敏感词word1和word2的文本。";
        Set<String> expected = Set.of("word1", "word2");
        Set<String> actual = sensitiveWordService.getSensitiveWords(text);
        assertEquals(expected, actual);
    }
}
