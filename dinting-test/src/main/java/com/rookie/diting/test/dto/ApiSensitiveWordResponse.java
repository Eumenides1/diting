package com.rookie.diting.test.dto;

import com.rookie.diting.domain.MatchedWord;
import com.rookie.diting.domain.SensitiveWordResult;
import lombok.Data;

import java.util.List;

/**
 * Name：ApiSensitiveWordResponse
 * Author：eumenides
 * Created on: 2025/1/15
 * Description:
 */
@Data
public class ApiSensitiveWordResponse {

    /**
     * 原始或处理后的文本
     */
    private String text;

    /**
     * 匹配的敏感词列表
     */
    private List<MatchedWord> matchedWords;

    /**
     * 是否包含敏感词
     */
    private boolean hasSensitiveWord;

    /**
     * 匹配到的敏感词数量
     */
    private int matchedCount;

    /**
     * 时间戳，记录结果生成时间
     */
    private long timestamp;

    /**
     * 状态：SUCCESS 或 ERROR
     */
    private String status;

    /**
     * 错误描述信息（仅在 status=ERROR 时存在）
     */
    private String errorMessage;

    /**
     * 静态方法：将 SensitiveWordResult 转换为 ApiSensitiveWordResponse
     *
     * @param result SensitiveWordResult 对象
     * @return 转换后的 ApiSensitiveWordResponse 对象
     */
    public static ApiSensitiveWordResponse from(SensitiveWordResult result) {
        ApiSensitiveWordResponse response = new ApiSensitiveWordResponse();
        response.setText(result.getText());
        response.setMatchedWords(result.getMatchedWords());
        response.setHasSensitiveWord(result.isHasSensitiveWord());
        response.setMatchedCount(result.getMatchedWords() == null ? 0 : result.getMatchedWords().size());
        response.setTimestamp(result.getTimestamp());
        response.setStatus(result.getStatus());
        response.setErrorMessage(result.getErrorMessage());
        return response;
    }
}
