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
        String outputFile = "src/main/resources/all.txt";
        String inputFile = "src/main/resources/all.enc";
        String key = "t+PVQbfPn/Or0sqIa/EUnQ==";

        EncryptionUtils.decryptFile(inputFile, outputFile, key);
        System.out.println("File encrypted successfully.");
    }
}
