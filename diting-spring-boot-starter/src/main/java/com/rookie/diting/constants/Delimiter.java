package com.rookie.diting.constants;

/**
 * 常见分隔符的枚举定义
 */
public enum Delimiter {
    NEWLINE("\\n"),
    COMMA(","),
    SEMICOLON(";"),
    PIPE("|");

    private final String value;

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据用户输入的 key 获取分隔符值
     */
    public static String getDelimiterValue(String key) {
        for (Delimiter delimiter : values()) {
            if (delimiter.name().equalsIgnoreCase(key)) {
                return delimiter.getValue();
            }
        }
        throw new IllegalArgumentException("Unsupported delimiter key: " + key);
    }
}
