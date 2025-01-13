package com.rookie.diting.test;

import com.rookie.diting.core.context.SensitiveWordContext;
import com.rookie.diting.core.loader.SensitiveWordLoader;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ACTreeConcurrentTest {

    public static void main(String[] args) throws Exception {
        // 1. 初始化测试数据
        Set<String> sensitiveWords = generateSensitiveWords(100000); // 生成10万敏感词
        SensitiveWordContext context = new SensitiveWordContext(new MockSensitiveWordLoader(sensitiveWords));
        context.init(); // 初始化 AC 自动机

        String testText = generateRandomText(10_000_000); // 1千万字符

        // 2. 配置线程池
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threadCount = availableProcessors * 2; // 动态线程数
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // 3. 并发性能测试
        long concurrentStartTime = System.nanoTime();

        List<Future<Void>> futures = new ArrayList<>();
        int iterationsPerThread = 10; // 每线程检测 10 次
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                for (int j = 0; j < iterationsPerThread; j++) {
                    context.findSensitiveWords(testText); // 执行检测
                }
                return null;
            }));
        }

        // 等待所有线程完成
        for (Future<Void> future : futures) {
            future.get();
        }

        long concurrentEndTime = System.nanoTime();

        // 计算总耗时和 TPS
        long totalCharacters = testText.length() * iterationsPerThread * threadCount;
        double totalSeconds = (concurrentEndTime - concurrentStartTime) / 1_000_000_000.0;
        double tps = totalCharacters / totalSeconds;

        executor.shutdown();

        System.out.printf("总检测字符数: %d\n", totalCharacters);
        System.out.printf("总耗时: %.2f 秒\n", totalSeconds);
        System.out.printf("每秒检测字符数 (TPS): %.2f 字符/秒\n", tps);
    }

    private static Set<String> generateSensitiveWords(int count) {
        Set<String> words = new HashSet<>();
        for (int i = 0; i < count; i++) {
            words.add("敏感词" + i);
        }
        return words;
    }

    private static String generateRandomText(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26))); // 随机生成英文字符
        }
        return sb.toString();
    }

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