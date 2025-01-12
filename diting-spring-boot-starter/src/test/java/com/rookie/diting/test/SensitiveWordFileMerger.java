package com.rookie.diting.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Name：com.rookie.diting.test.SensitiveWordFileMerger
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
public class SensitiveWordFileMerger {

    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String ALL_FILE_NAME = "all.txt";

    public static void main(String[] args) throws IOException {
        appendSensitiveWordsToAll();
    }

    /**
     * 将 resources 目录下其他 txt 文件的内容追加到 all.txt，去重处理
     */
    public static void appendSensitiveWordsToAll() throws IOException {
        // 1. 定义敏感词文件目录和需要排除的文件名
        File resourceDir = new File(RESOURCE_PATH);
        String allFilePath = RESOURCE_PATH + ALL_FILE_NAME;

        // 2. 获取 resources 目录下的所有 .txt 文件，排除 all.txt
        List<File> txtFiles = Arrays.stream(Objects.requireNonNull(resourceDir.listFiles()))
                .filter(file -> file.getName().endsWith(".txt") && !file.getName().equals(ALL_FILE_NAME))
                .collect(Collectors.toList());

        // 3. 读取 all.txt 的已有内容（如果文件存在）
        Set<String> allWords = new HashSet<>();
        Path allPath = Paths.get(allFilePath);
        if (Files.exists(allPath)) {
            List<String> existingWords = Files.readAllLines(allPath);
            allWords.addAll(existingWords);
        }

        // 4. 读取其他 txt 文件的内容并合并
        for (File file : txtFiles) {
            System.out.println("Processing file: " + file.getName());
            List<String> lines = Files.readAllLines(file.toPath());
            allWords.addAll(lines); // 合并到集合中，自动去重
        }

        // 5. 将合并后的内容追加写回 all.txt
        List<String> sortedWords = allWords.stream().sorted().collect(Collectors.toList()); // 排序后写入
        Files.write(allPath, sortedWords, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Merged and de-duplicated words written to all.txt");
        System.out.println("Total unique words: " + allWords.size());
    }
}
