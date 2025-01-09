package com.rookie.diting.loader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashSet;
import java.util.Set;


/**
 * Name：JsonWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: JSON 配置加载器
 */

public class JsonWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWordLoader.class);
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
