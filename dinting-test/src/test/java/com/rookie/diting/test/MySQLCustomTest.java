package com.rookie.diting.test;

import com.rookie.diting.config.DitingAutoConfiguration;
import com.rookie.diting.config.DitingProperties;
import com.rookie.diting.loader.impl.SensitiveWordLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {DitingAutoConfiguration.class})
public class MySQLCustomTest {

    @Autowired
    private SensitiveWordLoader loader;


    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Test
    public void testDatasourceConfig() {
        System.out.println("Datasource URL: " + datasourceUrl);
    }

    @Test
    public void testLoadSensitiveWordsWithConditions() throws Exception {
        // 加载敏感词
        Set<String> words = loader.loadSensitiveWords();

        // 验证结果
        assertNotNull(words);
        assertTrue(words.contains("word1"));
        assertTrue(words.contains("word2"));
        assertEquals(2, words.size()); // 确认符合条件的数据量
    }

    @Test
    public void testGetConfigMapWithValidKey() {
        DitingProperties properties = new DitingProperties();
        Map<String, Object> config = Map.of(
            "table", "sensitive_words",
            "conditions", Map.of(
                "is_active", "1",
                "type", "prohibited"
            )
        );
        properties.setConfig(config);

        Map<String, String> conditions = properties.getConfigMap("conditions");
        assertEquals("1", conditions.get("is_active"));
        assertEquals("prohibited", conditions.get("type"));
    }

    @Test
    public void testGetConfigMapWithInvalidKey() {
        DitingProperties properties = new DitingProperties();
        Map<String, Object> config = Map.of(
            "table", "sensitive_words"
        );
        properties.setConfig(config);

        assertThrows(IllegalArgumentException.class, () -> properties.getConfigMap("conditions"));
    }

    @Test
    public void testGetConfigMapWithNonMapValue() {
        DitingProperties properties = new DitingProperties();
        Map<String, Object> config = Map.of(
            "table", "sensitive_words",
            "columns", "word"
        );
        properties.setConfig(config);

        assertThrows(IllegalArgumentException.class, () -> properties.getConfigMap("columns"));
    }

    @Test
    public void testResolveDelimiter() {
        DitingProperties properties = new DitingProperties();
        properties.setDelimiter("COMMA");
        assertEquals(",", properties.resolveDelimiter());

        properties.setDelimiter("NEWLINE");
        assertEquals("\n", properties.resolveDelimiter());

        properties.setDelimiter("SEMICOLON");
        assertEquals(";", properties.resolveDelimiter());
    }
}
