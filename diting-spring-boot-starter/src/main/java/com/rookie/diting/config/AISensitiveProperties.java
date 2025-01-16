package com.rookie.diting.config;

import com.rookie.diting.constants.AIModelType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Name：AISensitiveProperties
 * Author：eumenides
 * Created on: 2025/1/16
 * Description:
 */
@ConfigurationProperties(prefix = "sensitive-word.ai")
@Data
public class AISensitiveProperties {
    /**
     * 是否启用 AI 敏感词识别
     */
    private boolean enabled = false;

    /**
     * AI 模型的类型（如 GPT、OpenAI、其他大模型）
     */
    private String modelType = AIModelType.OPEN_AI.getName();

    /**
     * 使用的大模型的基础模型，如 gpt4、o1mini
     */
    private String model = AIModelType.OPEN_AI.getDefaultModel();

    /**
     * AI 模型的 API Key
     */
    private String apiKey;

    /**
     * AI 模型的 secret （部分模型使用的是 apiKey 的形式）
     */
    private String secret;

    /**
     * 默认请求路径
     */
    private String baseUrl;

}
