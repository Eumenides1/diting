package com.rookie.diting.core.strategy.impl;

import com.rookie.diting.core.strategy.DesensitizationStrategy;

/**
 * @Classname BlurStrategy
 * @Description 模糊处理策略
 * @Date 2025/1/13 17:17
 * @Created by liujiapeng
 */
public class BlurStrategy implements DesensitizationStrategy {
    private static final String BLURRED_TEXT = "***";

    @Override
    public String desensitize(String word) {
        return BLURRED_TEXT;
    }

    @Override
    public void setReplacementChar(char replacementChar) {
        // 模糊处理不需要替换字符
    }
}
