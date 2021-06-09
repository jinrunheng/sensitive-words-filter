package com.duby.util.TrieFilter;

import java.util.Map;
import java.util.Objects;

public class Trie {
    private TrieNode root;

    public TrieNode getRoot() {
        return root;
    }

    public Trie() {
        root = new TrieNode();
    }

    /**
     * 将敏感词添加到 Trie 中
     */
    public void put(String word) {
        if (Objects.isNull(word) || word.length() == 0) {
            return;
        }
        if (!exist(word)) {
            char[] chars = word.toCharArray();
            TrieNode node = root;
            for (int i = 0; i < chars.length; i++) {
                Map<Character, TrieNode> nextNodes = node.getNextNodes();
                if (!nextNodes.containsKey(chars[i])) {
                    nextNodes.put(chars[i], new TrieNode());
                }
                node = nextNodes.get(chars[i]);
                node.setPath(node.getPath() + 1);
            }
            node.setEnd(true);
        }
    }

    /**
     * 查询在 Trie 当中是否存在某个敏感词
     */
    public boolean exist(String word) {
        if (Objects.isNull(word) || word.length() == 0) {
            return false;
        }
        char[] chars = word.toCharArray();
        TrieNode node = root;
        for (int i = 0; i < chars.length; i++) {
            Map<Character, TrieNode> nextNodes = node.getNextNodes();
            if (!nextNodes.containsKey(chars[i])) {
                return false;
            }
            node = nextNodes.get(chars[i]);
            if (node.getPath() == 0) {
                return false;
            }
        }
        return node.isEnd();
    }

    /**
     * 删除一个敏感词
     */
    public void remove(String word) {
        if (exist(word)) {
            char[] chars = word.toCharArray();
            TrieNode node = root;
            for (int i = 0; i < chars.length; i++) {
                Map<Character, TrieNode> nextNodes = node.getNextNodes();
                node = nextNodes.get(chars[i]);
                node.setPath(node.getPath() - 1);
            }
            node.setEnd(false);
        }
    }

}
