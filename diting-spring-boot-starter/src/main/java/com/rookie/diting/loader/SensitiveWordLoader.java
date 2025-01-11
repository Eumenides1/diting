package com.rookie.diting.loader;

import java.util.List;
import java.util.Set;

/**
 * Name：SensitiveWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: 敏感词加载接口
 */
public interface SensitiveWordLoader {
    Set<String> loadSensitiveWords() throws Exception;
}
