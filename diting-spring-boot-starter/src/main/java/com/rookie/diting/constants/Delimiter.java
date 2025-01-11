package com.rookie.diting.constants;

/**
 * 常见分隔符的枚举定义
 *
 * @author eumenides
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

    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据用户输入的 key 获取分隔符值
     *
     * @param key a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
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
