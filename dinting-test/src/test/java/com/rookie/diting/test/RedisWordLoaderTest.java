package com.rookie.diting.test;

import com.rookie.diting.loader.impl.RedisWordLoader;
import com.rookie.diting.loader.impl.SensitiveWordLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Name：RedisWordLoaderTest
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
@SpringBootTest
public class RedisWordLoaderTest {

    @Value("${sensitive-word.config.key}")
    private String redisKey;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testLoadSensitiveWords() throws Exception {
        // 插入测试数据到 Redis
        redisTemplate.opsForSet().add(redisKey, "test", "example", "sensitive");

        // 使用 Redis 加载器
        SensitiveWordLoader loader = new RedisWordLoader(redisTemplate, redisKey);

        // 加载敏感词
        Set<String> words = loader.loadSensitiveWords();

        // 验证敏感词是否正确加载
        assertTrue(words.contains("test"));
        assertTrue(words.contains("example"));
        assertTrue(words.contains("sensitive"));
    }
}
