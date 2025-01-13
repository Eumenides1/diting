package com.rookie.diting.constants;

/**
 * @Classname ReplacementType
 * @Description 替换字符的类型
 * @Date 2025/1/13 17:55
 * @Created by liujiapeng
 */
public enum ReplacementType {
    ASTERISK('*'), // 使用 * 替换
    HASH('#'),     // 使用 # 替换
    UNDERSCORE('_'); // 使用 _ 替换

    private final char replacementChar;

    ReplacementType(char replacementChar) {
        this.replacementChar = replacementChar;
    }

    public char getReplacementChar() {
        return replacementChar;
    }
}
