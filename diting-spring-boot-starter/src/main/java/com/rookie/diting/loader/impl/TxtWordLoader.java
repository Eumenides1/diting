package com.rookie.diting.loader.impl;

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
 */
public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtWordLoader.class);

    // 默认分隔符为换行
    private String delimiter = "\\n";
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from TXT file: {} with delimiter: {}", resourcePath, delimiter);
        // 加载敏感词
        List<String> words = loadWordsFromTxt();
        LOGGER.info("Loaded sensitive words: {}", words);
        return words;
    }
    /**
     * 从 TXT 文件中加载敏感词
     */
    private List<String> loadWordsFromTxt() throws Exception {
        Set<String> words = new HashSet<>();
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // 保证所有内容读取
            }
            String[] wordArray = content.toString().split(delimiter);
            for (String word : wordArray) {
                if (!word.trim().isEmpty()) {
                    words.add(word.trim());
                }
            }
        }
        return new ArrayList<>(words);
    }
}
