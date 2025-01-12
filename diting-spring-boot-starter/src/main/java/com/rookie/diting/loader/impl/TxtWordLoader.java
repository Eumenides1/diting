package com.rookie.diting.loader.impl;

import com.rookie.diting.constants.Delimiter;
import com.rookie.diting.loader.SensitiveWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Name：TxtWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: TXT 文件加载器
 *
 * @author eumenides
 */
public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtWordLoader.class);

    private String delimiter = "\\n"; // 默认分隔符为换行
    private List<String> resourcePaths; // 支持多个文件路径

    public void setResourcePaths(List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        // 如果未设置 delimiter，使用默认值
        if (this.delimiter == null || this.delimiter.isEmpty()) {
            this.delimiter = Delimiter.NEWLINE.getValue();
        }
        LOGGER.info("Loading sensitive words from TXT files: {} with delimiter: {}", resourcePaths, delimiter);
        Set<String> words = new HashSet<>();
        for (String resourcePath : resourcePaths) {
            words.addAll(loadWordsFromTxt(resourcePath));
        }
        return words;
    }

    private Set<String> loadWordsFromTxt(String resourcePath) throws Exception {
        Set<String> words = new HashSet<>();
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split(delimiter)) {
                    if (!word.trim().isEmpty()) {
                        words.add(word.trim());
                    }
                }
            }
        }
        LOGGER.info("Loaded {} sensitive words from {}", words.size(), resourcePath);
        return words;
    }
}
