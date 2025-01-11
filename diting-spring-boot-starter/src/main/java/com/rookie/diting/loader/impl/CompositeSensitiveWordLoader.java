package com.rookie.diting.loader.impl;

import com.rookie.diting.loader.SensitiveWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Name：CompositeSensitiveWordLoader
 * Author：eumenides
 * Created on: 2025/1/11
 * Description:
 */
public class CompositeSensitiveWordLoader implements SensitiveWordLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeSensitiveWordLoader.class);

    private final List<SensitiveWordLoader> loaders;

    public CompositeSensitiveWordLoader(List<SensitiveWordLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        Set<String> combinedWords = new HashSet<>();
        for (SensitiveWordLoader loader : loaders) {
            try {
                Set<String> words = loader.loadSensitiveWords();
                LOGGER.info("Loaded {} words from loader: {}", words.size(), loader.getClass().getSimpleName());
                combinedWords.addAll(words);
            } catch (Exception e) {
                LOGGER.error("Error loading sensitive words from loader: {}", loader.getClass().getSimpleName(), e);
                throw e; // 或者选择 continue;
            }
        }
        LOGGER.info("Total sensitive words loaded: {}", combinedWords.size());
        return combinedWords;
    }
}
