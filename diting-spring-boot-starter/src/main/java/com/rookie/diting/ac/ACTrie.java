package com.rookie.diting.ac;

import java.util.*;

/**
 * <p>ACTrie class.</p>
 *
 * @Classname ACTrie
 * @Description AC 树
 * @Date 2025/1/10 14:38
 * @Created by liujiapeng
 * @author eumenides
 */
public class ACTrie {

    private final ACNode root;

    /**
     * <p>Constructor for ACTrie.</p>
     *
     * @param keywords a {@link java.util.List} object
     */
    public ACTrie(List<String> keywords) {
        root = new ACNode();
        buildTrie(keywords);
        buildFailurePointers();
    }

    /**
     * 构建 Trie 树
     */
    private void buildTrie(List<String> keywords) {
        for (String keyword : keywords) {
            ACNode node = root;
            for (char c : keyword.toCharArray()) {
                node = node.children.computeIfAbsent(c, k -> new ACNode());
            }
            node.isEnd = true;
            node.output.add(keyword);
        }
    }

    /**
     * 构建失败指针
     */
    private void buildFailurePointers() {
        Queue<ACNode> queue = new LinkedList<>();

        // 初始化根节点的直接子节点
        for (ACNode child : root.children.values()) {
            child.failure = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            ACNode current = queue.poll();

            for (Map.Entry<Character, ACNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                ACNode child = entry.getValue();

                // 设置 failure 指针
                ACNode failure = current.failure;
                while (failure != root && !failure.children.containsKey(c)) {
                    failure = failure.failure;
                }
                if (failure.children.containsKey(c) && failure.children.get(c) != child) {
                    child.failure = failure.children.get(c);
                } else {
                    child.failure = root;
                }

                // 始终合并失败节点的输出，无条件
                child.output.addAll(child.failure.output);

                // 将子节点加入队列
                queue.add(child);
            }
        }
    }

    /**
     * 搜索文本中的敏感词，并返回找到的敏感词集合
     *
     * @param text 需要搜索的文本
     * @return 找到的敏感词集合
     */
    public List<String> findSensitiveWords(String text) {
        List<String> sensitiveWords = new ArrayList<>();
        ACNode currentNode = root;
        char[] chars = text.toCharArray();

        for (char c : chars) {
            while (currentNode != root && !currentNode.children.containsKey(c)) {
                currentNode = currentNode.failure;
            }
            currentNode = currentNode.children.getOrDefault(c, root);

            if (!currentNode.output.isEmpty()) {
                sensitiveWords.addAll(currentNode.output);
            }
        }
        return sensitiveWords;
    }

    /**
     * AC 树节点定义
     */
    private static class ACNode {
        Map<Character, ACNode> children = new HashMap<>();
        ACNode failure = null;
        boolean isEnd = false;
        Set<String> output = new HashSet<>();

        ACNode() {}
    }
}
