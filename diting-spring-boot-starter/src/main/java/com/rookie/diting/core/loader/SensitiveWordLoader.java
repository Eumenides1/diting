package com.rookie.diting.core.loader;

import java.util.Set;

/**
 * Name：SensitiveWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: 敏感词加载接口
 *
 * @author eumenides
 */
public interface SensitiveWordLoader {
    /**
     * <p>loadSensitiveWords.</p>
     *
     * @return a {@link java.util.Set} object
     * @throws java.lang.Exception if any.
     */
    Set<String> loadSensitiveWords() throws Exception;
}
