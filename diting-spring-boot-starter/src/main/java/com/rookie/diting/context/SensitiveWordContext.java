package com.rookie.diting.context;


import com.rookie.diting.ac.ACTrie;
import com.rookie.diting.loader.SensitiveWordLoader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname SensitiveWordContext
 * @Description 敏感词匹配上下文
 * @Date 2025/1/10 16:27
 * @Created by liujiapeng
 */
@Component
public class SensitiveWordContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordContext.class);

    private final SensitiveWordLoader loader;
    private ACTrie acTrie; // AC 自动机树

    @Autowired
    public SensitiveWordContext(SensitiveWordLoader loader) {
        this.loader = loader;
    }
    /**
     * 初始化 AC 自动机树
     */
    @PostConstruct
    public void init() throws Exception {
        LOGGER.info("Initializing AC trie...");
        List<String> sensitiveWords = loader.loadSensitiveWords();
        this.acTrie = new ACTrie();
        this.acTrie.createACTrie(sensitiveWords); // 构建 AC 自动机树
        LOGGER.info("AC trie initialized.");
    }

    /**
     * 获取 AC 自动机树
     */
    public ACTrie getAcTrie() {
        if (acTrie == null) {
            throw new IllegalStateException("AC trie has not been initialized.");
        }
        return acTrie;
    }
}
