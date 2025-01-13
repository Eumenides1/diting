package com.rookie.diting.test;

import com.rookie.diting.utils.EncryptionUtils;

/**
 * Name：FileEncryptor
 * Author：eumenides
 * Created on: 2025/1/13
 * Description:
 */
public class FileEncryptor {
    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/violence_terror.txt";
        String outputFile = "src/main/resources/violence_terror.enc";
        String key = "";

        EncryptionUtils.encryptFile(inputFile, outputFile, key);
        System.out.println("File encrypted successfully.");
    }
}
