package com.rookie.diting.config;

import com.rookie.diting.constants.Delimiter;
import com.rookie.diting.constants.SensitiveWordType;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Description;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Name：DitingProperties
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 *
 * @author eumenides
 */



@ConfigurationProperties(prefix = "sensitive-word")
@Data
public class DitingProperties {
    @Description("敏感词库加载方式")
    private Loaders loaders;
    @Description("是否开启默认敏感词库加载")
    private DefaultLoader defaultLoader;
    // 控制台相关配置
    @Description("是否启用控制台页面")
    private boolean consoleEnabled = true;
    @Description("用户选择的敏感词类型，支持一种或多种，默认是 all")
    private List<String> enabledTypes = Collections.singletonList("all"); // 默认是 "all"

    @Data
    public static class Loaders {
        private Txt txt;
        private Json json;
        private Mysql mysql;
        private Redis redis;

        @Data
        public static class Txt {
            private boolean enabled;
            @NotNull
            private List<String> filePath;
            private String delimiter = Delimiter.NEWLINE.getValue(); // 设置默认值为换行符
        }
        @Data
        public static class Json {
            private boolean enabled;
            private String filePath;
        }
        @Data
        public static class Mysql {
            private boolean enabled;
            private String table;
            private String columns;
            private Map<String,String> conditions;
        }
        @Data
        public static class Redis {
            private boolean enabled;
            private String key;
        }
    }
    @Data
    public static class DefaultLoader {
        private boolean enabled = true; // 默认启用
    }
}
