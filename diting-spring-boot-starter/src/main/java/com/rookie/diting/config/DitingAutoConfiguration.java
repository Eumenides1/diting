package com.rookie.diting.config;

import com.rookie.diting.loader.SensitiveWordLoader;
import com.rookie.diting.loader.impl.MySqlWordLoader;
import com.rookie.diting.loader.impl.RedisWordLoader;
import com.rookie.diting.loader.impl.JsonWordLoader;
import com.rookie.diting.loader.impl.TxtWordLoader;
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
        String filePath = properties.getConfig().get("filePath").toString();
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
        String filePath = properties.getConfig().get("filePath").toString();
        LOGGER.info("Configuring JsonWordLoader with file path: {}", filePath);

        JsonWordLoader jsonLoader = new JsonWordLoader();
        jsonLoader.setResourcePath(filePath);
        return jsonLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "MYSQL")
    public SensitiveWordLoader mysqlWordLoader(DataSource dataSource, DitingProperties properties) {
        if (dataSource == null) {
            LOGGER.error("DataSource is null. Please check your configuration.");
            throw new IllegalStateException("DataSource is null. Please check your configuration.");
        }
        LOGGER.info("Configuring MySqlWordLoader with table: {}, columns: {}, conditions: {}",
                properties.getConfig().get("table"),
                properties.getConfig().getOrDefault("columns", "word"),
                properties.getConfigMap("conditions"));

        return new MySqlWordLoader(dataSource, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "REDIS")
    public SensitiveWordLoader redisWordLoader(RedisTemplate<String, String> redisTemplate, DitingProperties properties) {
        validateConfig(properties, "key");
        String key = properties.getConfig().get("key").toString();
        LOGGER.info("Configuring RedisWordLoader with key: {}", key);

        return new RedisWordLoader(redisTemplate, key);
    }

    private void validateConfig(DitingProperties properties, String requiredKey) {
        if (!properties.getConfig().containsKey(requiredKey)) {
            throw new IllegalArgumentException(
                    "Missing required config key '" + requiredKey + "' for loader type " + properties.getLoaderType());
        }
    }
}