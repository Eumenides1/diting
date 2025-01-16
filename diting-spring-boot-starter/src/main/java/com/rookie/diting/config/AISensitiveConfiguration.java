package com.rookie.diting.config;

import com.rookie.diting.constants.AIModelType;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * Name：AISensitiveConfiguration
 * Author：eumenides
 * Created on: 2025/1/16
 * Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AISensitiveProperties.class)
public class AISensitiveConfiguration {

    @Resource
    AISensitiveProperties aiSensitiveProperties;

    private final Environment environment;

    public AISensitiveConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @ConditionalOnProperty(prefix = "sensitive-word.ai", name = "enabled", havingValue = "true")
    public ChatModel chatModel() {
        log.info("Inside ChatModel,{}", aiSensitiveProperties.toString());
        var openAiApi = new OpenAiApi(
                environment.getProperty("spring.ai.openai.base-url"),
                environment.getProperty("spring.ai.openai.api-key")
        );

        var openAiChatOptions = OpenAiChatOptions.builder()
                .model(environment.getProperty("spring.ai.openai.chat.options.model"))
                .build();
        log.info("Inside OpenAiChatOptions,{}", openAiChatOptions);

        var chat = new OpenAiChatModel(openAiApi, openAiChatOptions);
        String result = chat.call("Hi 你好呀");
        log.info("Inside ChatModel,{}", result);
        return chat;
    }
}
