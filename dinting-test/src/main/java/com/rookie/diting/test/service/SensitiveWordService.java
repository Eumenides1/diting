package com.rookie.diting.test.service;

import com.rookie.diting.context.SensitiveWordContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SensitiveWordService
 * @Description TODO
 * @Date 2025/1/10 16:29
 * @Created by liujiapeng
 */
@Service
public class SensitiveWordService {

    private final SensitiveWordContext sensitiveWordContext;

    @Autowired
    public SensitiveWordService(SensitiveWordContext sensitiveWordContext) {
        this.sensitiveWordContext = sensitiveWordContext;
    }

    /**
     * 判断文本是否包含敏感词
     */
    public List<String> containsSensitiveWord(String text) {
        return sensitiveWordContext.getAcTrie().findSensitiveWords(text);
    }

    /**
     * 替换文本中的敏感词
     */
    public String replaceSensitiveWords(String text) {
        return sensitiveWordContext.getAcTrie().match(text);
    }
}
