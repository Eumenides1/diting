package com.rookie.diting.core.loader.impl;

import com.rookie.diting.constants.Delimiter;
import com.rookie.diting.core.loader.SensitiveWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Name：TxtWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: TXT 文件加载器
 *
 * @author eumenides
 */
public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtWordLoader.class);

    private final static String CIPHER_KEY = "t+PVQbfPn/Or0sqIa/EUnQ==";

    private final static String CIPHER_ALGORITHM = "AES";

    private String delimiter = "\\n"; // 默认分隔符为换行
    private List<String> resourcePaths; // 支持多个文件路径

    public void setResourcePaths(List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from TXT files: {}", resourcePaths);
        Set<String> words = new HashSet<>();
        for (String resourcePath : resourcePaths) {
            if (resourcePath.endsWith(".enc")) {
                LOGGER.info("Detected encrypted file: {}", resourcePath);
                processDecryptedFile(resourcePath, words);
            } else {
                LOGGER.info("Loading plain text file: {}", resourcePath);
                words.addAll(loadWordsFromTxt(resourcePath));
            }
        }
        LOGGER.info("Loaded {} sensitive words.", words.size());
        return words;
    }

    private void processDecryptedFile(String resourcePath, Set<String> words) throws Exception {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (InputStream is = resource.getInputStream();
             CipherInputStream cis = new CipherInputStream(is, getCipher(Cipher.DECRYPT_MODE));
             BufferedReader reader = new BufferedReader(new InputStreamReader(cis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line.trim());
                }
            }
        }
    }

    private Set<String> loadWordsFromTxt(String resourcePath) throws Exception {
        Set<String> words = new HashSet<>();
        ClassPathResource resource = new ClassPathResource(resourcePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split(delimiter)) {
                    if (!word.trim().isEmpty()) {
                        words.add(word.trim());
                    }
                }
            }
        }

        return words;
    }

    private Cipher getCipher(int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(CIPHER_KEY.getBytes(), CIPHER_ALGORITHM);
        cipher.init(mode, secretKey);
        return cipher;
    }
}
