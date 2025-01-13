package com.rookie.diting.domain;

import lombok.Data;

/**
 * @Classname MatchedWord
 * @Description TODO
 * @Date 2025/1/13 16:39
 * @Created by liujiapeng
 */
@Data
public class MatchedWord {
    private String word;
    private int startIndex;
    private int endIndex;
    private float score;
    private String category;
}
