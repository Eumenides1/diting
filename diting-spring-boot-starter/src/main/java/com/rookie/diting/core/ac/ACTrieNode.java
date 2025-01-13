package com.rookie.diting.core.ac;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>ACTrieNode class.</p>
 *
 * @Classname ACTrieNode
 * @Description AC自动机-子节点
 * @Date 2025/1/10 14:31
 * @Created by liujiapeng
 * @author eumenides
 */
@Getter
@Setter
public class ACTrieNode {
    // 子节点
    private Map<Character, ACTrieNode> children = new HashMap<>();

    // 匹配过程中，如果模式串不匹配，模式串指针会回退到failover继续进行匹配
    private ACTrieNode failover = null;

    private int depth;

    private boolean isLeaf = false;

    /**
     * 如果当前节点的子节点中不存在字符 c 对应的节点，则创建一个新的 ACTrieNode 对象
     *
     * @param c a char
     */
    public void addChildrenIfAbsent(char c) {
        children.computeIfAbsent(c, (key) -> new ACTrieNode());
    }
    /**
     * 返回当前节点的子节点中字符 c 对应的节点，如果不存在则返回 null。
     *
     * @param c a char
     * @return a {@link ACTrieNode} object
     */
    public ACTrieNode childOf(char c) {
        return children.get(c);
    }
    /**
     * <p>hasChild.</p>
     *
     * @param c a char
     * @return a boolean
     */
    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ACTrieNode{" +
                "failover=" + failover +
                ", depth=" + depth +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
