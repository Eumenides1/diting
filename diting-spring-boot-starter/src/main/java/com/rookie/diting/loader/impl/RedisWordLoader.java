package com.rookie.diting.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
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
        LOGGER.info("Loading sensitive words from Redis key: " + key);
        Set<String> words = new HashSet<>(redisTemplate.opsForSet().members(key));
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}