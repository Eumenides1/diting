package com.rookie.diting.service;

import com.rookie.diting.ac.ACTrie;
import com.rookie.diting.context.SensitiveWordContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Name：SensitiveWordService
 * Author：eumenides
 * Created on: 2025/1/11
 * Description:
 */
@Service
public class SensitiveWordService {

    private final SensitiveWordContext sensitiveWordContext;

    @Autowired
    public SensitiveWordService(SensitiveWordContext sensitiveWordContext) {
        this.sensitiveWordContext = sensitiveWordContext;
    }

    /**
     * 检查文本是否包含敏感词
     *
     * @param text 需要检查的文本
     * @return 是否包含敏感词
     */
    public boolean containsSensitiveWord(String text) {
        List<String> foundWords = sensitiveWordContext.findSensitiveWords(text);
        return !foundWords.isEmpty();
    }

    /**
     * 替换文本中的敏感词
     *
     * @param text    需要替换的文本
     * @param replace 替换字符
     * @return 替换后的文本
     */
    public String replaceSensitiveWords(String text, char replace) {
        List<String> foundWords = sensitiveWordContext.findSensitiveWords(text);
        String result = text;
        for (String word : foundWords) {
            String replacement = repeatChar(replace, word.length());
            result = result.replace(word, replacement);
        }
        return result;
    }

    /**
     * 获取文本中的敏感词
     *
     * @param text 需要检测的文本
     * @return 敏感词集合
     */
    public Set<String> getSensitiveWords(String text) {
        List<String> foundWords = sensitiveWordContext.findSensitiveWords(text);
        return foundWords.stream().collect(Collectors.toSet());
    }

    /**
     * 辅助方法：生成重复字符的字符串
     *
     * @param c     替换字符
     * @param count 重复次数
     * @return 生成的字符串
     */
    private String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for(int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}