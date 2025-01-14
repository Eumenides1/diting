package com.rookie.diting.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Classname SensitiveWordExpander
 * @Description 中拼繁混合词库扩充算法
 * @Date 2025/1/14 15:02
 * @Created by liujiapeng
 */
public class SensitiveWordExpander {

    /**
     * 判断字符是否是汉字
     *
     * @param c 字符
     * @return 如果是汉字返回 true，否则返回 false
     */
    public static boolean isChineseCharacter(char c) {
        return Character.toString(c).matches("[\\u4E00-\\u9FA5]");
    }

    /**
     * 将汉字转换为拼音
     *
     * @param chinese 汉字字符串
     * @return 拼音字符串
     */
    public static String toPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 拼音小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不带声调
        StringBuilder pinyin = new StringBuilder();
        for (char c : chinese.toCharArray()) {
            if (isChineseCharacter(c)) { // 只处理汉字
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArray != null && pinyinArray.length > 0) {
                        pinyin.append(pinyinArray[0]); // 取第一个拼音
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
        }
        return pinyin.toString();
    }
    /**
     * 将简体中文转换为繁体中文（如果已经是繁体中文，则不转换）
     *
     * @param text 输入的文本
     * @return 转换后的繁体中文文本
     */
    public static String toTraditionalIfSimplified(String text) {
        String traditionalText = ZhConverterUtil.toTraditional(text);
        // 如果转换后的文本与原始文本相同，说明原始文本已经是繁体中文
        return traditionalText.equals(text) ? text : traditionalText;
    }
    /**
     * 扩充敏感词（生成拼音和繁体形式）
     *
     * @param word 原始敏感词
     * @return 扩充后的敏感词集合
     */
    public static Set<String> expandWord(String word) {
        Set<String> expandedWords = new HashSet<>();
        expandedWords.add(word); // 添加原始敏感词

        // 生成拼音形式
        String pinyin = toPinyin(word);
        if (!pinyin.isEmpty()) {
            expandedWords.add(pinyin);
        }

        // 生成繁体形式（仅当原始词是简体中文时）
        String traditionalWord = toTraditionalIfSimplified(word);
        if (!traditionalWord.equals(word)) {
            expandedWords.add(traditionalWord);
        }

        return expandedWords;
    }
    /**
     * 在原文件基础上扩充敏感词
     *
     * @param filePath 敏感词文件路径
     */
    public static void expandSensitiveWordsInPlace(String filePath) {
        Set<String> originalWords = new LinkedHashSet<>(); // 存储原始敏感词
        Set<String> expandedWords = new LinkedHashSet<>(); // 存储扩充后的敏感词

        // 读取原文件中的敏感词
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.trim().isEmpty() && word.matches(".*[\\u4E00-\\u9FA5]+.*")) {
                    originalWords.add(word); // 添加原始敏感词
                    expandedWords.addAll(expandWord(word)); // 扩充敏感词
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将扩充后的敏感词追加到原文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true 表示追加模式
            writer.newLine(); // 换行
            writer.write("# 以下是扩充的敏感词："); // 添加注释
            writer.newLine();
            for (String word : expandedWords) {
                if (!originalWords.contains(word)) { // 避免重复追加
                    writer.write(word);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 扩充敏感词文件
     *
     * @param inputFile  输入的敏感词文件路径
     * @param outputFile 输出的扩充后的敏感词文件路径
     */
    public static void expandSensitiveWords(String inputFile, String outputFile) {
        Set<String> expandedWords = new HashSet<>(); // 使用集合去重

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // 忽略空行和非中文字符
                if (word.trim().isEmpty() || !word.matches(".*[\\u4E00-\\u9FA5]+.*")) {
                    continue;
                }
                // 扩充敏感词
                expandedWords.addAll(expandWord(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将扩充后的敏感词写入输出文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String word : expandedWords) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
