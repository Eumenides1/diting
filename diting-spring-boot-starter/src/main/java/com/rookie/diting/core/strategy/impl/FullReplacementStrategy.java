package com.rookie.diting.core.strategy.impl;

import com.rookie.diting.core.strategy.DesensitizationStrategy;

/**
 * @Classname FullReplacementStrategy
 * @Description 完全替换策略
 * @Date 2025/1/13 17:15
 * @Created by liujiapeng
 */
public class FullReplacementStrategy implements DesensitizationStrategy {
    private char replacementChar = '*'; // 默认替换字符

    @Override
    public String desensitize(String word) {
        return String.valueOf(replacementChar).repeat(word.length());
    }

    @Override
    public void setReplacementChar(char replacementChar) {
        this.replacementChar = replacementChar;
    }
}
