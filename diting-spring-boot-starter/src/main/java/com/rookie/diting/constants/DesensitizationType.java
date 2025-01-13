package com.rookie.diting.constants;

import com.rookie.diting.core.strategy.DesensitizationStrategy;
import com.rookie.diting.core.strategy.impl.BlurStrategy;
import com.rookie.diting.core.strategy.impl.FullReplacementStrategy;
import com.rookie.diting.core.strategy.impl.PartialReplacementStrategy;

/**
 * @Classname DesensitizationType
 * @Description TODO
 * @Date 2025/1/13 17:18
 * @Created by liujiapeng
 */
public enum DesensitizationType {
    FULL_REPLACEMENT(new FullReplacementStrategy()), // 完全替换
    PARTIAL_REPLACEMENT(new PartialReplacementStrategy()), // 部分替换
    BLUR(new BlurStrategy()); // 模糊处理

    private final DesensitizationStrategy strategy;

    DesensitizationType(DesensitizationStrategy strategy) {
        this.strategy = strategy;
    }

    public DesensitizationStrategy getStrategy() {
        return strategy;
    }

    /**
     * 设置替换字符
     *
     * @param replacementChar 替换字符
     */
    public void setReplacementChar(char replacementChar) {
        strategy.setReplacementChar(replacementChar);
    }
}
