package com.rookie.diting.loader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rookie.diting.core.ac.ACTrie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    public List<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from JSON file: " + resourcePath);

        // 加载 JSON 文件
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ObjectMapper mapper = new ObjectMapper();

        // 直接读取 JSON 文件为 List<String>
        List<String> words = mapper.readValue(resource.getInputStream(), List.class);
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
