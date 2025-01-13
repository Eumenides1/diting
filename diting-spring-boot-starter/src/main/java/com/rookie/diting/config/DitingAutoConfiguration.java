package com.rookie.diting.config;

import com.rookie.diting.constants.SensitiveWordType;
import com.rookie.diting.core.loader.SensitiveWordLoader;
import com.rookie.diting.core.loader.impl.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;


/**
 * Name：DitingAutoConfiguration
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 *
 * @author eumenides
 */
@Configuration
@EnableConfigurationProperties(DitingProperties.class)
public class DitingAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DitingAutoConfiguration.class);

    @Resource
    private DitingProperties properties;

    /**
     * <p>txtWordLoader.</p>
     *
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.loaders.txt", name = "enabled", havingValue = "true")
    public SensitiveWordLoader txtWordLoader() {
        DitingProperties.Loaders.Txt txt = properties.getLoaders().getTxt();
        validateConfig(txt.getFilePath(), "filePath for TXT loader");
        LOGGER.info("Configuring TxtWordLoader with file path: {} and delimiter: {}", txt.getFilePath(), txt.getDelimiter());
        TxtWordLoader txtLoader = new TxtWordLoader();
        txtLoader.setResourcePaths(txt.getFilePath());
        txtLoader.setDelimiter(txt.getDelimiter());
        return txtLoader;
    }

    /**
     * <p>jsonWordLoader.</p>
     *
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.loaders.json", name = "enabled", havingValue = "true")
    public SensitiveWordLoader jsonWordLoader() {
        DitingProperties.Loaders.Json json = properties.getLoaders().getJson();
        validateConfig(json.getFilePath(), "filePath for JSON loader");
        LOGGER.info("Configuring JsonWordLoader with file path: {}", json.getFilePath());

        JsonWordLoader jsonLoader = new JsonWordLoader();
        jsonLoader.setResourcePath(json.getFilePath());
        return jsonLoader;
    }

    /**
     * <p>mysqlWordLoader.</p>
     *
     * @param dataSource a {@link javax.sql.DataSource} object
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.loaders.mysql", name = "enabled", havingValue = "true")
    public SensitiveWordLoader mysqlWordLoader(DataSource dataSource) {
        DitingProperties.Loaders.Mysql mysql = properties.getLoaders().getMysql();
        validateConfig(mysql.getTable(), "table for MySQL loader");
        LOGGER.info("Configuring MySqlWordLoader with table: {}, columns: {}, conditions: {}",
                mysql.getTable(),
                mysql.getColumns(),
                mysql.getConditions());

        return new MySqlWordLoader(dataSource, mysql.getTable(), mysql.getColumns(), mysql.getConditions());
    }

    /**
     * <p>redisWordLoader.</p>
     *
     * @param redisTemplate a {@link org.springframework.data.redis.core.RedisTemplate} object
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.loaders.redis", name = "enabled", havingValue = "true")
    public SensitiveWordLoader redisWordLoader(RedisTemplate<String, String> redisTemplate) {
        DitingProperties.Loaders.Redis redis = properties.getLoaders().getRedis();
        validateConfig(redis.getKey(), "key for Redis loader");
        LOGGER.info("Configuring RedisWordLoader with key: {}", redis.getKey());

        return new RedisWordLoader(redisTemplate, redis.getKey());
    }

    // 默认的 TxtWordLoader，加载组件内置的敏感词库
    /**
     * <p>defaultWordLoader.</p>
     *
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.default-loader", name = "enabled", havingValue = "true", matchIfMissing = true)
    public SensitiveWordLoader defaultWordLoader() {
        List<String> enabledTypes = properties.getEnabledTypes(); // 确保是 List<String>
        List<String> filePaths;

        if (enabledTypes.contains(SensitiveWordType.ALL.getName())) {
            // 如果是 "all"，只加载 all.txt
            filePaths = List.of(SensitiveWordType.ALL.getFilename());
        } else {
            // 加载用户指定类型
            filePaths = enabledTypes.stream()
                    .map(SensitiveWordType::fromName)
                    .filter(Objects::nonNull) // 过滤 null 值
                    .map(SensitiveWordType::getFilename)
                    .toList();
        }

        LOGGER.info("Loading default sensitive words from files: {}", filePaths);

        // 创建并配置 TxtWordLoader
        TxtWordLoader defaultLoader = new TxtWordLoader();
        defaultLoader.setResourcePaths(filePaths); // 修正为使用 List<String>
        defaultLoader.setDelimiter("\n");
        return defaultLoader;
    }
    /**
     * 创建 CompositeSensitiveWordLoader，将所有启用的加载器组合起来
     *
     * @param loaders a {@link java.util.List} object
     * @return a {@link SensitiveWordLoader} object
     */
    @Bean
    @Primary
    public SensitiveWordLoader compositeSensitiveWordLoader(List<SensitiveWordLoader> loaders) {
        LOGGER.info("Creating CompositeSensitiveWordLoader with {} loaders.", loaders.size());
        return new CompositeSensitiveWordLoader(loaders);
    }

    private void validateConfig(Object value, String configName) {
        if (value == null) {
            throw new IllegalArgumentException("Missing required config: " + configName);
        }

        if (value instanceof String strValue) {
            if (strValue.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid config: " + configName + " is empty");
            }
        } else if (value instanceof List<?> listValue) {
            if (listValue.isEmpty()) {
                throw new IllegalArgumentException("Invalid config: " + configName + " is an empty list");
            }
            for (Object item : listValue) {
                if (!(item instanceof String strItem) || strItem.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid config: " + configName + " contains invalid value: " + item);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid config: " + configName + " is of unsupported type: " + value.getClass());
        }
    }
}
