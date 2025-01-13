package com.rookie.diting.core.strategy.impl;

import com.rookie.diting.core.strategy.DesensitizationStrategy;

/**
 * @Classname PartialReplacementStrategy
 * @Description 部分替换策略
 * @Date 2025/1/13 17:16
 * @Created by liujiapeng
 */
public class PartialReplacementStrategy implements DesensitizationStrategy {
    private char replacementChar = '*'; // 默认替换字符

    @Override
    public String desensitize(String word) {
        if (word.length() <= 2) {
            return String.valueOf(replacementChar).repeat(word.length());
        }
        return word.charAt(0) +
                String.valueOf(replacementChar).repeat(word.length() - 2) +
                word.charAt(word.length() - 1);
    }

    @Override
    public void setReplacementChar(char replacementChar) {
        this.replacementChar = replacementChar;
    }
}
