package com.rookie.diting.loader.impl;

import java.util.List;
import java.util.Set;

/**
 * Name：SensitiveWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: 敏感词加载接口
 */
public interface SensitiveWordLoader {
    List<String> loadSensitiveWords() throws Exception;
}
