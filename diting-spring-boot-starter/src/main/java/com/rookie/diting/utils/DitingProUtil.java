package com.rookie.diting.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rookie.diting.constants.SensitiveWordType;
import com.rookie.diting.core.context.SensitiveWordContext;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Name：DitingProUtil
 * Author：eumenides
 * Created on: 2025/1/16
 * Description:
 */
@Component
public class DitingProUtil {

    private static ChatModel chatModel;

    @Autowired
    public void setSensitiveWordContext(ChatModel chatModel) {
        if (DitingProUtil.chatModel == null) {
            DitingProUtil.chatModel = chatModel;
        }
    }

    public static SensitiveWordResult getSensitiveWord(String word) {
        String systemPrompt = """
                我希望你作为一名高级文本审核专家，能够检测并分类以下文本中的所有敏感内容，同时识别用户可能通过特殊手段绕过检测的行为，包括方言表达。\s
                
                需要检测的敏感内容包括（不限于）：
                1. 政治敏感：涉及政治人物、政府、政策争议等内容。
                2. 色情内容：明确的性暗示、性描述或不适当的性语言。
                3. 暴力内容：威胁、暴力语言或描述身体伤害的内容。
                4. 用户隐私：如身份证号、护照号、电话号码、家庭住址、银行卡号等信息。
                5. 诈骗信息：包含虚假承诺、非法转账、伪造身份或其他诈骗行为。
                6. 金融行为：涉及未经授权的转账请求、贷款信息或其他可疑金融操作。
                7. 辱骂内容：使用侮辱性语言，包括普通话、拼音、繁体字、英文简写、字符变形，以及可能的方言表达。
                
                **用户可能通过以下手段试图绕过检测，请一并考虑：**
                - **拼音替代**：如 "jian she" 替代 "建设"。
                - **繁体字替代**：如 "暴力" 替代为 "暴力"。
                - **英文简写**：如 "SB" 替代 "傻逼"。
                - **字符变形**：如 "傻 B" 或 "沙B"。
                - **方言表达**：如 "港普"中的“扑街”或“死八婆”，以及地方语言表达的侮辱性内容。
                
                请对以下文本进行分析：
                "{words}"
                
                你的任务是：
                1. 判断文本中是否包含敏感内容；
                2. 如果包含，请标明具体的敏感类别，并列出相关的敏感词；
                3. 对文本的整体敏感程度进行评分（0-100），分数越高表示敏感性越强；
                4. 如有可能，对敏感内容提供合理解释或补充说明。
                """;

        return ChatClient.create(chatModel).prompt()
                .user(word)
                .system(s -> s.text(systemPrompt)
                        .param("words", word)
                ).call().entity(SensitiveWordResult.class);
    }


    public record SensitiveWordResult(
            @JsonProperty(required = true, value = "containsSensitive") boolean containsSensitive,
            @JsonProperty(required = true, value = "words") List<String> words,
            @JsonProperty(required = true, value = "type") SensitiveWordType type,
            @JsonProperty(required = true, value = "originalText" ) String originalText,
            @JsonProperty(required = true, value = "sensitiveScore") int sensitiveScore
    ){}

}
