package com.rookie.diting.loader.impl;

import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Name：TxtWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: TXT 文件加载器
 */

public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(TxtWordLoader.class.getName());
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from TXT file: " + resourcePath);
        Set<String> words = new HashSet<>();

        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        }

        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
