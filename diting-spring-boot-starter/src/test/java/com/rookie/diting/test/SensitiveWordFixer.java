package com.rookie.diting.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Name：SensitiveWordFixer
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
public class SensitiveWordFixer {

    private static final String INPUT_FILE_PATH = "src/main/resources/COVID-19.txt"; // 输入文件路径
    private static final String OUTPUT_FILE_PATH = "src/main/resources/covid.txt"; // 输出文件路径

    public static void main(String[] args) throws IOException {
        fixSensitiveWords();
    }

    /**
     * 将带有 + 号的词语分割，并写入换行分隔的新文件
     */
    public static void fixSensitiveWords() throws IOException {
        // 1. 读取输入文件的内容
        Path inputFilePath = Paths.get(INPUT_FILE_PATH);
        Path outputFilePath = Paths.get(OUTPUT_FILE_PATH);

        // 确保文件存在
        if (!Files.exists(inputFilePath)) {
            throw new FileNotFoundException("输入文件未找到: " + INPUT_FILE_PATH);
        }

        // 2. 读取所有行并处理
        try (BufferedReader reader = Files.newBufferedReader(inputFilePath);
             BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 使用 "+" 拆分行，并逐个写入输出文件
                String[] words = line.split("\\+");
                for (String word : words) {
                    writer.write(word.trim()); // 去掉多余空格
                    writer.newLine(); // 换行
                }
            }
        }

        System.out.println("处理完成，结果已保存到: " + OUTPUT_FILE_PATH);
    }
}
