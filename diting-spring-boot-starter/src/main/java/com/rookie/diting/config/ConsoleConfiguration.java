package com.rookie.diting.config;

import com.rookie.diting.controller.SensitiveWordController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Name：ConsoleConfiguration
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
@ConditionalOnProperty(prefix = "sensitive-word", name = "console-enabled", havingValue = "true", matchIfMissing = true)
public class ConsoleConfiguration {

    @Bean
    public SensitiveWordController sensitiveWordController() {
        return new SensitiveWordController();
    }
}
