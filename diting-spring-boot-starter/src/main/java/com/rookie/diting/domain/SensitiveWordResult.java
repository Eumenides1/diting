package com.rookie.diting.domain;

import lombok.Data;

import java.util.List;

/**
 * @Classname SensitiveWordResult
 * @Description TODO
 * @Date 2025/1/13 16:38
 * @Created by liujiapeng
 */
@Data
public class SensitiveWordResult {
    private String text;
    private List<MatchedWord> matchedWords;
    private boolean hasSensitiveWord;
    private long timestamp;
    private String status;
    private String errorMessage;
}
