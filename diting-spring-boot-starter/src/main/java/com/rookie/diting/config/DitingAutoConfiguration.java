package com.rookie.diting.config;

import com.rookie.diting.constants.Delimiter;
import com.rookie.diting.loader.SensitiveWordLoader;
import com.rookie.diting.loader.impl.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.util.List;


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
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.loaders.txt", name = "enabled", havingValue = "true")
    public SensitiveWordLoader txtWordLoader() {
        DitingProperties.Loaders.Txt txt = properties.getLoaders().getTxt();
        validateConfig(txt.getFilePath(), "filePath for TXT loader");
        LOGGER.info("Configuring TxtWordLoader with file path: {} and delimiter: {}", txt.getFilePath(), txt.getDelimiter());
        TxtWordLoader txtLoader = new TxtWordLoader();
        txtLoader.setResourcePath(txt.getFilePath());
        txtLoader.setDelimiter(txt.getDelimiter());
        return txtLoader;
    }

    /**
     * <p>jsonWordLoader.</p>
     *
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
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
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
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
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
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
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
     */
    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.default-loader", name = "enabled", havingValue = "true", matchIfMissing = true)
    public SensitiveWordLoader defaultWordLoader() {
        String defaultFilePath = "sensitive_word_dict.txt"; // 内置敏感词库路径
        LOGGER.info("Loading default sensitive words from: {}", defaultFilePath);

        TxtWordLoader defaultLoader = new TxtWordLoader();
        defaultLoader.setResourcePath(defaultFilePath);
        defaultLoader.setDelimiter("\n"); // 内置敏感词库使用换行分隔
        return defaultLoader;
    }
    /**
     * 创建 CompositeSensitiveWordLoader，将所有启用的加载器组合起来
     *
     * @param loaders a {@link java.util.List} object
     * @return a {@link com.rookie.diting.loader.SensitiveWordLoader} object
     */
    @Bean
    @Primary
    public SensitiveWordLoader compositeSensitiveWordLoader(List<SensitiveWordLoader> loaders) {
        LOGGER.info("Creating CompositeSensitiveWordLoader with {} loaders.", loaders.size());
        return new CompositeSensitiveWordLoader(loaders);
    }

    private void validateConfig(String value, String configName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing required config: " + configName);
        }
    }
}
