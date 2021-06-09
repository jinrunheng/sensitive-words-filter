package com.duby.util.TrieFilter;

import com.duby.util.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

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
    public void batchAdd(String filePath) {
        try (BufferedReader reader = FileReader.readFile(filePath)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                trie.put(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replace(char[] chars, char r, int start, int end) {
        if (start < 0 || end >= chars.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (start >= end) {
            throw new RuntimeException("Invalid Operation");
        }
        for (int i = start; i <= end; i++) {
            chars[i] = r;
        }
    }

    /**
     * @param sentence 需要过滤的句子
     * @param r  敏感词替换字符
     * @return 过滤后的句子
     * <p>
     * p1,p2 指针指向 sentence
     * TrieNode node 指向 Trie
     * <p>
     * case1:
     * p2 指向的当前字符不在敏感词库中，并且 node 指向 root ，则 p1,p2 一起向后移动
     * case2:
     * p2 指向的当前字符在敏感词库中，并且 node.isEnd == false ，并且 p2 并没有指向最后一个字符，则 node,p2 一起移动；
     * 如果 node.isEnd == true 则将 p1~p2-1 之间的字符用 replace 替换，然后 p1 指向 p2，node 指向 root
     * 如果 p2 已经指向最后一个字符，则只移动 node,并判断移动后的 node.isEnd == true ，如果为 true 则将 p1~p2 之间的字符用 replace 替换
     * case3:
     * p2 指向的当前字符不在敏感词库中，并且 node 不指向 root，则 p1 移动一个单位，p2 指向 p1，node 指向 root
     *
     * <p>
     */
    public String filter(String sentence, char r) {
        char[] chars = sentence.toCharArray();
        int p1 = 0;
        int p2 = 0;
        TrieNode node = trie.getRoot();
        while (p2 < chars.length) {
            if (node.getNextNodes().containsKey(chars[p2]) && p2 != chars.length - 1) {
                if (!node.isEnd()) {
                    node = node.getNextNodes().get(chars[p2]);
                    p2++;
                } else {
                    replace(chars, r, p1, p2 - 1);
                    p1 = p2;
                    node = trie.getRoot();
                }

            } else if (node.getNextNodes().containsKey(chars[p2]) && p2 == chars.length - 1) {
                node = node.getNextNodes().get(chars[p2]);
                if (node.isEnd()) {
                    replace(chars, r, p1, p2);
                }
                p2++;
            } else {
                if(node.isEnd()){
                    replace(chars, r, p1, p2 - 1);
                    p1 = p2;
                    node = trie.getRoot();
                }
                if (node == trie.getRoot()) {
                    p1++;
                    p2++;
                } else {
                    p1++;
                    p2 = p1;
                    node = trie.getRoot();
                }
            }
        }

        return String.valueOf(chars);
    }

}
