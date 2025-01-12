package com.rookie.diting.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Name：ThymeleafTemplateResolverConfig
 * Author：eumenides
 * Created on: 2025/1/12
 * Description:
 */
@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
public class ThymeleafTemplateResolverConfig {

    @Bean
    public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/"); // 指定 Starter 的模板路径
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCacheable(false);
        return resolver;
    }
}
