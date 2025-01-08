package com.rookie.diting.service;

import com.rookie.diting.loader.impl.SensitiveWordLoader;

import java.util.Set;

/**
 * Name：SensitiveWordChecker
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
public class SensitiveWordChecker {
    private final Set<String> sensitiveWords;

    public SensitiveWordChecker(SensitiveWordLoader loader) throws Exception {
        this.sensitiveWords = loader.loadSensitiveWords();
    }

    public boolean containsSensitiveWord(String text) {
        for (String word : sensitiveWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getSensitiveWords() {
        return sensitiveWords;
    }
}
