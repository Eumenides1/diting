package com.rookie.diting.test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Name：KeyGeneratorUtil
 * Author：eumenides
 * Created on: 2025/1/13
 * Description:
 */
public class KeyGeneratorUtil {
    public static String generateAESKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize); // 128, 192, or 256
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String key = generateAESKey(128); // 生成 128 位 AES 密钥
        System.out.println("Generated Key: " + key);
    }
}
