package com.rookie.diting.core.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname SensitiveWordFilter
 * @Description TODO
 * @Date 2025/1/14 15:31
 * @Created by liujiapeng
 */
public class SensitiveWordFilter {

    private static final Set<Character> INTERFERENCE_SYMBOLS = new HashSet<>();
    static {
        // 常见干扰符号
        INTERFERENCE_SYMBOLS.add(' ');
        INTERFERENCE_SYMBOLS.add(',');
        INTERFERENCE_SYMBOLS.add('.');
        INTERFERENCE_SYMBOLS.add('!');
        INTERFERENCE_SYMBOLS.add('?');
        INTERFERENCE_SYMBOLS.add('@');
        INTERFERENCE_SYMBOLS.add('#');
        INTERFERENCE_SYMBOLS.add('$');
        INTERFERENCE_SYMBOLS.add('%');
        INTERFERENCE_SYMBOLS.add('&');
        INTERFERENCE_SYMBOLS.add('*');
        INTERFERENCE_SYMBOLS.add('(');
        INTERFERENCE_SYMBOLS.add(')');
        INTERFERENCE_SYMBOLS.add('-');
        INTERFERENCE_SYMBOLS.add('_');
        INTERFERENCE_SYMBOLS.add('+');
        INTERFERENCE_SYMBOLS.add('=');
        INTERFERENCE_SYMBOLS.add('{');
        INTERFERENCE_SYMBOLS.add('}');
        INTERFERENCE_SYMBOLS.add('[');
        INTERFERENCE_SYMBOLS.add(']');
        INTERFERENCE_SYMBOLS.add('|');
        INTERFERENCE_SYMBOLS.add('\\');
        INTERFERENCE_SYMBOLS.add('/');
        INTERFERENCE_SYMBOLS.add('<');
        INTERFERENCE_SYMBOLS.add('>');
        INTERFERENCE_SYMBOLS.add('~');
        INTERFERENCE_SYMBOLS.add('`');
        INTERFERENCE_SYMBOLS.add('^');

        // 圈圈里套着的数字（①、②、③ 等）
        for (int i = 0; i <= 20; i++) {
            INTERFERENCE_SYMBOLS.add((char) ('①' + i)); // ① 到 ⑳
        }

        // 其他 Unicode 特殊符号（可以根据需要扩展）
        INTERFERENCE_SYMBOLS.add('☆');
        INTERFERENCE_SYMBOLS.add('★');
        INTERFERENCE_SYMBOLS.add('☀');
        INTERFERENCE_SYMBOLS.add('☁');
        INTERFERENCE_SYMBOLS.add('☂');
        INTERFERENCE_SYMBOLS.add('☃');
        INTERFERENCE_SYMBOLS.add('♡');
        INTERFERENCE_SYMBOLS.add('♥');
        INTERFERENCE_SYMBOLS.add('♦');
        INTERFERENCE_SYMBOLS.add('♣');
        INTERFERENCE_SYMBOLS.add('♠');
        INTERFERENCE_SYMBOLS.add('✓');
        INTERFERENCE_SYMBOLS.add('✔');
        INTERFERENCE_SYMBOLS.add('✕');
        INTERFERENCE_SYMBOLS.add('✖');
        INTERFERENCE_SYMBOLS.add('✿');
        INTERFERENCE_SYMBOLS.add('❀');
        INTERFERENCE_SYMBOLS.add('❤');
        INTERFERENCE_SYMBOLS.add('❥');
        INTERFERENCE_SYMBOLS.add('❦');
        INTERFERENCE_SYMBOLS.add('❧');
    }

    /**
     * 判断字符是否是干扰符号
     *
     * @param c 字符
     * @return 如果是干扰符号返回 true，否则返回 false
     */
    public static boolean isInterferenceSymbol(char c) {
        return INTERFERENCE_SYMBOLS.contains(c);
    }

}
