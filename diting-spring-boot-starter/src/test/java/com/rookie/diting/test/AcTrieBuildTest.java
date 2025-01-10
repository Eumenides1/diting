package com.rookie.diting.test;

import com.rookie.diting.ac.ACTrie;
import com.rookie.diting.loader.impl.TxtWordLoader;

import java.util.List;

/**
 * Name：AcTrieBuildTest
 * Author：eumenides
 * Created on: 2025/1/10
 * Description:
 */
public class AcTrieBuildTest {
    public static void main(String[] args) throws Exception {
        ACTrie acTrie = new ACTrie();
        TxtWordLoader txtWordLoader = new TxtWordLoader();
        txtWordLoader.setResourcePath("sensitive_word_dict.txt");
        List<String> strings = txtWordLoader.loadSensitiveWords();
        acTrie.createACTrie(strings);
    }
}
