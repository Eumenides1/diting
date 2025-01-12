package com.rookie.diting.test;

import com.rookie.diting.ac.ACTrie;
import com.rookie.diting.loader.impl.TxtWordLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Name：AcTrieBuildTest
 * Author：eumenides
 * Created on: 2025/1/10
 * Description:
 */
public class AcTrieBuildTest {
    public static void main(String[] args) throws Exception {
        TxtWordLoader txtWordLoader = new TxtWordLoader();
        txtWordLoader.setResourcePaths(Collections.singletonList("all.txt"));
        Set<String> strings = txtWordLoader.loadSensitiveWords();
        ACTrie acTrie = new ACTrie((new ArrayList<>(strings)));
        List<String> sensitiveWords = acTrie.findSensitiveWords("1989天安门");
        System.out.println(sensitiveWords);
    }
}
