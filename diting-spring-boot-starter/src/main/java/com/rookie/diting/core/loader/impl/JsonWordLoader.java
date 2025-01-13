package com.rookie.diting.core.loader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rookie.diting.core.loader.SensitiveWordLoader;
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
 *
 * @author eumenides
 */
public class JsonWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWordLoader.class);
    private String resourcePath;

    /**
     * <p>Setter for the field <code>resourcePath</code>.</p>
     *
     * @param resourcePath a {@link java.lang.String} object
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from JSON file: " + resourcePath);

        // 加载 JSON 文件
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ObjectMapper mapper = new ObjectMapper();

        // 直接读取 JSON 文件为 List<String>
        Set<String> words = mapper.readValue(resource.getInputStream(), HashSet.class);
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
