package com.rookie.diting.test;

import com.rookie.diting.context.SensitiveWordContext;
import com.rookie.diting.loader.SensitiveWordLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Name：ACTreeSingleThreadTest
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
public class ACTreeSingleThreadTest {

    public static void main(String[] args) throws Exception {
        // 1. 初始化测试数据
        Set<String> sensitiveWords = generateSensitiveWords(100000); // 生成10万敏感词
        SensitiveWordContext context = new SensitiveWordContext(new MockSensitiveWordLoader(sensitiveWords));
        context.init(); // 初始化 AC 自动机

        String testText = generateRandomText(100_000_000); // 生成1亿字符的测试文本

        // 2. 开始性能测试
        System.out.println("开始测试...");
        long startTime = System.nanoTime();

        List<String> result = context.findSensitiveWords(testText); // 检测敏感词

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // 3. 计算 TPS
        double seconds = duration / 1_000_000_000.0; // 转换为秒
        double tps = testText.length() / seconds;

        System.out.println("检测结果：" + result.size() + " 个敏感词");
        System.out.printf("测试文本总字符数: %d\n", testText.length());
        System.out.printf("总耗时: %.2f 秒\n", seconds);
        System.out.printf("每秒检测字符数 (TPS): %.2f 字符/秒\n", tps);
    }

    // 随机生成敏感词
    private static Set<String> generateSensitiveWords(int count) {
        Set<String> words = new HashSet<>();
        for (int i = 0; i < count; i++) {
            words.add("敏感词" + i);
        }
        return words;
    }


    // 随机生成测试文本
    private static String generateRandomText(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26))); // 随机生成英文字符
        }
        return sb.toString();
    }

    // Mock 敏感词加载器
    private static class MockSensitiveWordLoader implements SensitiveWordLoader {
        private final Set<String> words;

        public MockSensitiveWordLoader(Set<String> words) {
            this.words = words;
        }

        @Override
        public Set<String> loadSensitiveWords() throws Exception {
            return words;
        }
    }
}
