package com.rookie.diting.utils;

import com.rookie.diting.context.SensitiveWordContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static boolean containsSensitiveWord(String text) {
        ensureInitialized();
        return !sensitiveWordContext.findSensitiveWords(text).isEmpty();
    }

    /**
     * 替换文本中的敏感词
     *
     * @param text    需要替换的文本
     * @param replace 替换字符
     * @return 替换后的文本
     */
    public static String replaceSensitiveWords(String text, char replace) {
        ensureInitialized();
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
    public static Set<String> getSensitiveWords(String text) {
        ensureInitialized();
        return new HashSet<>(sensitiveWordContext.findSensitiveWords(text));
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


}
