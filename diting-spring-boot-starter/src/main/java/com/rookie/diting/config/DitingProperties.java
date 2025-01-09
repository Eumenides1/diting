package com.rookie.diting.config;

import com.rookie.diting.constants.Delimiter;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * Name：DitingProperties
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */

@ConfigurationProperties(prefix = "sensitive-word")
@Validated
public class DitingProperties {

    /**
     * 加载器类型 (TXT, JSON, MYSQL, REDIS)
     */
    @NotNull(message = "Loader type must be specified.")
    private LoaderType loaderType;

    /**
     * 配置参数 (根据 loaderType 的类型动态校验)
     */
    @NotNull(message = "Config must not be null.")
    private Map<String, String> config;

    /**
     * 分隔符，仅适用于 TXT 类型，支持常量
     * 默认值为换行符：NEWLINE
     */
    private String delimiter = "NEWLINE";

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(LoaderType loaderType) {
        this.loaderType = loaderType;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * 获取解析后的分隔符值
     */
    public String resolveDelimiter() {
        return Delimiter.getDelimiterValue(delimiter);
    }

    /**
     * 加载器类型的枚举
     */
    public enum LoaderType {
        TXT, JSON, MYSQL, REDIS
    }
}
