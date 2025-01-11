package com.rookie.diting.context;


import com.rookie.diting.ac.ACTrie;
import com.rookie.diting.loader.SensitiveWordLoader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public SensitiveWordContext(SensitiveWordLoader loader) {
        this.loader = loader;
    }

    /**
     * 初始化 AC 自动机树
     */
    @PostConstruct
    public void init() throws Exception {
        LOGGER.info("Initializing AC trie...");
        List<String> sensitiveWords = List.copyOf(loader.loadSensitiveWords());
        this.acTrie = new ACTrie(sensitiveWords);
        LOGGER.info("AC trie initialized with {} sensitive words.", sensitiveWords.size());
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

    /**
     * 重新加载敏感词库并刷新 AC 自动机树
     */
    public synchronized void reloadSensitiveWords() throws Exception {
        LOGGER.info("Reloading sensitive words...");
        List<String> sensitiveWords = List.copyOf(loader.loadSensitiveWords());
        this.acTrie = new ACTrie(sensitiveWords);
        LOGGER.info("AC trie reloaded with {} sensitive words.", sensitiveWords.size());
    }

    /**
     * 检测句子中的敏感词
     *
     * @param text 需要检测的文本
     * @return 句子中检测到的敏感词集合
     */
    public List<String> findSensitiveWords(String text) {
        ACTrie trie = getAcTrie();
        return trie.findSensitiveWords(text);
    }
}