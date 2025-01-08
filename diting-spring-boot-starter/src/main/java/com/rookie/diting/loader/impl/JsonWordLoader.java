package com.rookie.diting.loader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Name：JsonWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: JSON 配置加载器
 */

public class JsonWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(JsonWordLoader.class.getName());
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from JSON file: " + resourcePath);
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ObjectMapper mapper = new ObjectMapper();
        Set<String> words = new HashSet<>(mapper.readValue(resource.getInputStream(), Set.class));
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
