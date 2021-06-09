package com.duby.util.TrieFilter;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> nextNodes;
    private boolean isEnd;

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    private int path;

    public TrieNode() {
        nextNodes = new HashMap<>();
        isEnd = false;
        path = 0;
    }

    public Map<Character, TrieNode> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(Map<Character, TrieNode> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
