package com.rookie.diting.test;

import com.rookie.diting.config.DitingProperties;
import com.rookie.diting.constants.Delimiter;
import com.rookie.diting.loader.impl.TxtWordLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DitingPropertiesTest {

    @Test
    public void testDelimiterEnum() {
        assertEquals("\\n", Delimiter.getDelimiterValue("NEWLINE"));
        assertEquals(",", Delimiter.getDelimiterValue("COMMA"));
        assertEquals(";", Delimiter.getDelimiterValue("SEMICOLON"));
        assertEquals("|", Delimiter.getDelimiterValue("PIPE"));

        assertThrows(IllegalArgumentException.class, () -> Delimiter.getDelimiterValue("INVALID"));
    }

    @Test
    public void testResolveDelimiter() {
        DitingProperties properties = new DitingProperties();
        properties.setDelimiter("COMMA");
        assertEquals(",", properties.resolveDelimiter());

        properties.setDelimiter("NEWLINE");
        assertEquals("\\n", properties.resolveDelimiter());

        properties.setDelimiter("SEMICOLON");
        assertEquals(";", properties.resolveDelimiter());
    }

    @Test
    public void testTxtWordLoaderWithDelimiter() throws Exception {
        TxtWordLoader loader = new TxtWordLoader();
        loader.setResourcePath("sensitive_words.txt");

        // Test with comma delimiter
        loader.setDelimiter(",");
        Set<String> words = loader.loadSensitiveWords();
        assertTrue(words.contains("word1"));
        assertTrue(words.contains("word2"));
        assertTrue(words.contains("word3"));

        // Test with newline delimiter
        loader.setDelimiter("\\n");
        words = loader.loadSensitiveWords();
        assertTrue(words.contains("word4"));
        assertTrue(words.contains("word5"));
    }
}
