package com.rookie.diting.test.service;

import com.rookie.diting.service.SensitiveWordChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Classname SensitiveWordService
 * @Description TODO
 * @Date 2025/1/10 11:01
 * @Created by liujiapeng
 */
@Service
public class SensitiveWordService {
    private final SensitiveWordChecker sensitiveWordChecker;

    public SensitiveWordService(SensitiveWordChecker sensitiveWordChecker) {
        this.sensitiveWordChecker = sensitiveWordChecker;
    }

    public Set<String> checkSensitiveWords(String text) {
        return sensitiveWordChecker.getSensitiveWords();
    }
}
