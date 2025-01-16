package com.rookie.diting.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Name：CorsConfig
 * Author：eumenides
 * Created on: 2025/1/16
 * Description:
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有请求路径
                .allowedOrigins("*") // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许发送 Cookie
                .maxAge(3600); // 预检请求的有效期
    }
}
