package com.rookie.diting.test;

import com.rookie.diting.utils.SensitiveWordExpander;

import java.io.*;

/**
 * @Classname SensitiveWordExpanderTest
 * @Description TODO
 * @Date 2025/1/14 15:06
 * @Created by liujiapeng
 */
public class SensitiveWordExpanderTest {
    public static void main(String[] args) {
//        testIsChineseCharacter();
//        testToPinyin();
//        testToTraditionalIfSimplified();
//        testExpandWord();
        testExpandSensitiveWordsInPlace();
    }
    /**
     * 测试 isChineseCharacter 方法
     */
    private static void testIsChineseCharacter() {
        System.out.println("=== 测试 isChineseCharacter 方法 ===");
        char chineseChar = '中';
        char nonChineseChar = 'A';
        System.out.println("'中' 是汉字吗？ " + SensitiveWordExpander.isChineseCharacter(chineseChar)); // true
        System.out.println("'A' 是汉字吗？ " + SensitiveWordExpander.isChineseCharacter(nonChineseChar)); // false
        System.out.println();
    }

    /**
     * 测试 toPinyin 方法
     */
    private static void testToPinyin() {
        System.out.println("=== 测试 toPinyin 方法 ===");
        String chineseText = "中国";
        String mixedText = "中国ABC";
        System.out.println("'中国' 的拼音是: " + SensitiveWordExpander.toPinyin(chineseText)); // zhongguo
        System.out.println("'中国ABC' 的拼音是: " + SensitiveWordExpander.toPinyin(mixedText)); // zhongguo
        System.out.println();
    }

    /**
     * 测试 toTraditionalIfSimplified 方法
     */
    private static void testToTraditionalIfSimplified() {
        System.out.println("=== 测试 toTraditionalIfSimplified 方法 ===");
        String simplifiedText = "中国";
        String traditionalText = "中國";
        String nonChineseText = "ABC";
        System.out.println("'中国' 的繁体是: " + SensitiveWordExpander.toTraditionalIfSimplified(simplifiedText)); // 中國
        System.out.println("'中國' 的繁体是: " + SensitiveWordExpander.toTraditionalIfSimplified(traditionalText)); // 中國
        System.out.println("'ABC' 的繁体是: " + SensitiveWordExpander.toTraditionalIfSimplified(nonChineseText)); // ABC
        System.out.println();
    }

    /**
     * 测试 expandWord 方法
     */
    private static void testExpandWord() {
        System.out.println("=== 测试 expandWord 方法 ===");
        String word1 = "中国";
        String word2 = "阿扁";
        String word3 = "zhongguo";
        System.out.println("'中国' 的扩充结果: " + SensitiveWordExpander.expandWord(word1)); // [中国, zhongguo, 中國]
        System.out.println("'阿扁' 的扩充结果: " + SensitiveWordExpander.expandWord(word2)); // [阿扁, abian]
        System.out.println("'zhongguo' 的扩充结果: " + SensitiveWordExpander.expandWord(word3)); // [zhongguo]
        System.out.println();
    }
    /**
     * 测试 expandSensitiveWords 方法
     */
    private static void testExpandSensitiveWords() {
        System.out.println("=== 测试 expandSensitiveWords 方法 ===");
        String inputFile = "src/main/resources/dev_txt/covid.txt";  // 输入的敏感词文件
        String outputFile = "src/main/resources/dev_txt/covid_word.txt";  // 输出的扩充后的敏感词文件

        // 调用 expandSensitiveWords 方法
        SensitiveWordExpander.expandSensitiveWords(inputFile, outputFile);

        // 读取扩充后的敏感词文件并打印
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            System.out.println("扩充后的敏感词文件内容：");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void testExpandSensitiveWordsInPlace(){
        String filePath = "src/main/resources/dev_txt/violence_terror.txt";  // 敏感词文件路径
       SensitiveWordExpander.expandSensitiveWordsInPlace(filePath);
        System.out.println("敏感词库已在本文件基础上扩充！");
    }
}
