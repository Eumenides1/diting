package com.rookie.diting.config;

import com.rookie.diting.loader.impl.MySqlWordLoader;
import com.rookie.diting.loader.impl.RedisWordLoader;
import com.rookie.diting.loader.impl.SensitiveWordLoader;
import com.rookie.diting.loader.impl.JsonWordLoader;
import com.rookie.diting.loader.impl.TxtWordLoader;
import com.rookie.diting.service.SensitiveWordChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;


/**
 * Name：DitingAutoConfiguration
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
@Configuration
@EnableConfigurationProperties(DitingProperties.class)
public class DitingAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DitingAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "TXT")
    public SensitiveWordLoader txtWordLoader(DitingProperties properties) {
        validateConfig(properties, "filePath");
        String filePath = properties.getConfig().get("filePath");
        String delimiter = properties.resolveDelimiter(); // 解析用户友好的分隔符

        LOGGER.info("Configuring TxtWordLoader with file path: {} and delimiter: {}", filePath, delimiter);

        TxtWordLoader txtLoader = new TxtWordLoader();
        txtLoader.setResourcePath(filePath);
        txtLoader.setDelimiter(delimiter);
        return txtLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "JSON")
    public SensitiveWordLoader jsonWordLoader(DitingProperties properties) {
        validateConfig(properties, "filePath");
        String filePath = properties.getConfig().get("filePath");
        LOGGER.info("Configuring JsonWordLoader with file path: {}", filePath);

        JsonWordLoader jsonLoader = new JsonWordLoader();
        jsonLoader.setResourcePath(filePath);
        return jsonLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "MYSQL")
    public SensitiveWordLoader mysqlWordLoader(DataSource dataSource, DitingProperties properties) {
        validateConfig(properties, "table");
        String table = properties.getConfig().get("table");
        LOGGER.info("Configuring MySqlWordLoader with table: {}", table);

        return new MySqlWordLoader(dataSource, table);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "REDIS")
    public SensitiveWordLoader redisWordLoader(RedisTemplate<String, String> redisTemplate, DitingProperties properties) {
        validateConfig(properties, "key");
        String key = properties.getConfig().get("key");
        LOGGER.info("Configuring RedisWordLoader with key: {}", key);

        return new RedisWordLoader(redisTemplate, key);
    }

    @Bean
    @ConditionalOnMissingBean
    public SensitiveWordChecker sensitiveWordChecker(SensitiveWordLoader loader) throws Exception {
        return new SensitiveWordChecker(loader);
    }

    private void validateConfig(DitingProperties properties, String requiredKey) {
        if (!properties.getConfig().containsKey(requiredKey)) {
            throw new IllegalArgumentException(
                    "Missing required config key '" + requiredKey + "' for loader type " + properties.getLoaderType());
        }
    }
}