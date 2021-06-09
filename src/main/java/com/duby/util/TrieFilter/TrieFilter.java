package com.duby.util.TrieFilter;

import com.duby.util.FileReader;

import java.io.BufferedReader;
import java.io.IOException;

public class TrieFilter {

    private Trie trie;

    public TrieFilter() {
        trie = new Trie();
    }

    public Trie getTrie() {
        return trie;
    }

    /**
     * 将 sensi_words.txt 文件中的敏感词添加到 Trie中完成初始化
     */
    public void init() {
        batchAdd("sensi_words.txt");
    }

    /**
     * 增加一个敏感词
     */
    public void put(String word) {
        trie.put(word);
    }

    /**
     * 删除一个敏感词
     */
    public void remove(String word) {
        trie.remove(word);
    }

    /**
     * 查询当前的 TrieFilter 中是否存在某个敏感词
     */
    public boolean exist(String word) {
        return trie.exist(word);
    }

    /**
     * 批处理添加敏感词
     */
    public void batchAdd(String filePath){
        try (BufferedReader reader = FileReader.readFile(filePath)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                trie.put(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
