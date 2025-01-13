package com.rookie.diting.core.strategy;

/**
 * @Classname DesensitizationStrategy
 * @Description TODO
 * @Date 2025/1/13 17:14
 * @Created by liujiapeng
 */
public interface DesensitizationStrategy {
    /**
     * 对敏感词进行脱敏处理
     *
     * @param word 敏感词
     * @return 脱敏后的字符串
     */
    String desensitize(String word);

    /**
     * 设置替换字符
     *
     * @param replacementChar 替换字符
     */
    void setReplacementChar(char replacementChar);
}
