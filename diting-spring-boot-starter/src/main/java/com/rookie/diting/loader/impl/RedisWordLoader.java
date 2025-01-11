package com.rookie.diting.loader.impl;

import com.rookie.diting.loader.SensitiveWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Name：RedisWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: Redis 加载器，用于从 Redis 中加载敏感词。
 */
public class RedisWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisWordLoader.class.getName());
    private final RedisTemplate<String, String> redisTemplate;
    private final String key;

    public RedisWordLoader(RedisTemplate<String, String> redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from Redis key: {}, data type: {}", key, redisTemplate.type(key));
        // 加载敏感词
        Set<String> words = loadWordsFromRedis(redisTemplate.type(key));
        LOGGER.info("Loaded sensitive words: {}", words);
        return words;
    }

    private Set<String> loadWordsFromRedis(DataType dataType) {
        if (dataType == DataType.SET) {
            Set<String> wordSet = redisTemplate.opsForSet().members(key);
            if (wordSet == null || wordSet.isEmpty()) {
                throw new IllegalStateException("No sensitive words found in Redis key: " + key);
            }
            return wordSet;
        } else if (dataType == DataType.LIST) {
            List<String> wordSet = redisTemplate.opsForList().range(key, 0, -1);
            if (wordSet == null || wordSet.isEmpty()) {
                throw new IllegalStateException("No sensitive words found in Redis key: " + key);
            }
            return Set.copyOf(wordSet);
        } else {
            throw new IllegalArgumentException("Unsupported data type for key: " + key + ". Expected SET or LIST.");
        }
    }
}