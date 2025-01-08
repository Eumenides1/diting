package com.rookie.diting.test;

import com.rookie.diting.config.DitingAutoConfiguration;
import com.rookie.diting.loader.impl.JsonWordLoader;
import com.rookie.diting.loader.impl.SensitiveWordLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Name：JsonWordLoaderTest
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
@SpringBootTest(classes = DitingAutoConfiguration.class)
public class JsonWordLoaderTest {

    @Autowired
    private SensitiveWordLoader loader;

    @Test
    public void testLoadSensitiveWords() throws Exception {
        Set<String> words = loader.loadSensitiveWords();

        assertTrue(words.contains("test"));
        assertTrue(words.contains("example"));
        assertTrue(words.contains("sensitive"));
    }
}
