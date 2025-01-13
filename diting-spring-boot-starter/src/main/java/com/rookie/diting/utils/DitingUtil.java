package com.rookie.diting.utils;

import com.rookie.diting.constants.DesensitizationType;
import com.rookie.diting.constants.ReplacementType;
import com.rookie.diting.core.context.SensitiveWordContext;
import com.rookie.diting.core.strategy.DesensitizationStrategy;
import com.rookie.diting.domain.MatchedWord;
import com.rookie.diting.domain.SensitiveWordResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Name：DitingUtil
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
@Component
public class DitingUtil {
    private static SensitiveWordContext sensitiveWordContext;

    /**
     * 设置上下文实例，由 Spring 自动注入
     * @param context SensitiveWordContext 实例
     */
    @Autowired
    public void setSensitiveWordContext(SensitiveWordContext context) {
        if (DitingUtil.sensitiveWordContext == null) {
            DitingUtil.sensitiveWordContext = context;
        }
    }

    /**
     * 检查文本是否包含敏感词
     *
     * @param text 需要检查的文本
     * @return 是否包含敏感词
     */
    /**
     * 检查文本是否包含敏感词
     *
     * @param text 需要检查的文本
     * @return 是否包含敏感词
     */
    public static SensitiveWordResult containsSensitiveWord(String text) {
        ensureInitialized();
        List<String> sensitiveWords = sensitiveWordContext.findSensitiveWords(text);
        return convertToSensitiveWordResult(text, sensitiveWords);
    }

    /**
     * 替换文本中的敏感词
     *
     * @param text    需要替换的文本
     * @return 替换后的文本及匹配结果
     */
    public static SensitiveWordResult replaceSensitiveWords(String text, DesensitizationType desensitizationType, ReplacementType replacementChar) {
        ensureInitialized();
        SensitiveWordResult result = new SensitiveWordResult();
        try {
            // 获取敏感词列表
            List<String> sensitiveWords = sensitiveWordContext.findSensitiveWords(text);
            // 获取脱敏策略
            DesensitizationStrategy strategy = desensitizationType.getStrategy();
            strategy.setReplacementChar(replacementChar.getReplacementChar());
            // 替换敏感词
            String sanitizedText = text;
            for (String word : sensitiveWords) {
                // 使用 replacementChar 替换敏感词的字符
                String replacement = strategy.desensitize(word);
                sanitizedText = sanitizedText.replace(word, replacement);
            }
            result = convertToSensitiveWordResult(sanitizedText, sensitiveWords); // 复用公共逻辑
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    public static SensitiveWordResult replaceSensitiveWords(String text, DesensitizationType desensitizationType) {
        return replaceSensitiveWords(text, desensitizationType, ReplacementType.ASTERISK);
    }


    /**
     * 获取文本中的敏感词
     *
     * @param text 需要检测的文本
     * @return 敏感词集合及匹配结果
     */
    /**
     * 获取文本中的敏感词
     *
     * @param text 需要检测的文本
     * @return 敏感词集合及匹配结果
     */
    public static SensitiveWordResult getSensitiveWords(String text) {
        ensureInitialized();
        List<String> sensitiveWords = sensitiveWordContext.findSensitiveWords(text);
        return convertToSensitiveWordResult(text, sensitiveWords);
    }

    /**
     * 手动刷新敏感词库
     *
     * @return 刷新结果
     * @throws Exception 如果刷新敏感词库失败
     */
    public static SensitiveWordResult reloadSensitiveWords() throws Exception {
        ensureInitialized();
        sensitiveWordContext.reloadSensitiveWords();
        SensitiveWordResult result = new SensitiveWordResult();
        result.setStatus("SUCCESS");
        result.setErrorMessage(null);
        return result;
    }

    /**
     * 确保上下文已初始化
     */
    private static void ensureInitialized() {
        if (sensitiveWordContext == null) {
            throw new IllegalStateException("SensitiveWordContext has not been initialized. Please check Spring configuration.");
        }
    }

    /**
     * 辅助方法：生成重复字符的字符串
     *
     * @param c     替换字符
     * @param count 重复次数
     * @return 生成的字符串
     */
    private static String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 将敏感词列表转换为 SensitiveWordResult 对象
     *
     * @param text          原始文本
     * @param sensitiveWords 敏感词列表
     * @return SensitiveWordResult 对象
     */
    private static SensitiveWordResult convertToSensitiveWordResult(String text, List<String> sensitiveWords) {
        SensitiveWordResult result = new SensitiveWordResult();
        result.setText(text);

        // 将敏感词列表转换为 MatchedWord 列表
        List<MatchedWord> matchedWords = sensitiveWords.stream()
                .map(word -> {
                    MatchedWord matchedWord = new MatchedWord();
                    matchedWord.setWord(word);
                    matchedWord.setStartIndex(text.indexOf(word)); // 计算起始位置
                    matchedWord.setEndIndex(matchedWord.getStartIndex() + word.length()); // 计算结束位置
                    matchedWord.setCategory("default"); // 默认分类
                    return matchedWord;
                })
                .collect(Collectors.toList());

        result.setMatchedWords(matchedWords);
        result.setHasSensitiveWord(!matchedWords.isEmpty());
        result.setTimestamp(System.currentTimeMillis());
        result.setStatus("SUCCESS");
        return result;
    }
}
