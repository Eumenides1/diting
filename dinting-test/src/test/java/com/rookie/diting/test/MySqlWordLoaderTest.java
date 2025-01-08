package com.rookie.diting.test;

import com.rookie.diting.config.DitingAutoConfiguration;
import com.rookie.diting.loader.impl.MySqlWordLoader;
import com.rookie.diting.loader.impl.SensitiveWordLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Name：MySqlWordLoaderTest
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
@SpringBootTest
public class MySqlWordLoaderTest {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testLoadSensitiveWords() throws Exception {
        // 使用 MySQL 加载器
        SensitiveWordLoader loader = new MySqlWordLoader(dataSource, "sensitive_words");

        // 加载敏感词
        Set<String> words = loader.loadSensitiveWords();

        // 验证敏感词是否正确加载
        assertTrue(words.contains("test"));
        assertTrue(words.contains("example"));
        assertTrue(words.contains("sensitive"));
    }
}
