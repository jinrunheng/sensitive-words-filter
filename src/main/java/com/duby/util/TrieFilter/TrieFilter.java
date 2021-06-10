package com.duby.util.TrieFilter;

import com.duby.util.FileReader;
import org.apache.commons.lang3.CharUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class TrieFilter {

    private Trie trie;

    public TrieFilter() {
        trie = new Trie();
    }

    public Trie getTrie() {
        return trie;
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

    protected void replace(char[] chars, char r, int start, int end) {
        if (start < 0 || end >= chars.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (start > end) {
            throw new RuntimeException("Invalid Operation");
        }
        for (int i = start; i <= end; i++) {
            chars[i] = r;
        }
    }

    /**
     * 判断是否为符号
     */
    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0X2E80 || c > 0x9FFF);
    }

    /**
     * @param sentence 需要过滤的句子
     * @param r        敏感词替换字符
     * @return 过滤后的句子
     * <p>
     * p1,p2 指针指向 sentence
     * TrieNode node 指向 Trie
     * <p>
     * case1:
     * p2 指向的当前字符不在敏感词库中，node.isEnd == false，并且 node 指向 root ，则 p1,p2 一起向后移动
     * p2 指向的当前字符不在敏感词库中，node.isEnd == false，并且 node 不指向 root，则 p1 移动一个单位，p2 指向 p1，node 指向 root
     * p2 指向的当前字符不再敏感词库中，node.isEnd == true，则将 p1~p2-1 之间的字符用 r 替换，然后 p1 指向 p2，node 指向 root
     * case2:
     * p2 指向的当前字符在敏感词库中，p2 并没有指向最后一个字符，并且 node.isEnd == false，则 node,p2 一起移动
     * p2 指向的当前字符在敏感词库中，p2 并没有指向最后一个字符，并且 node.isEnd == true， 则将 p1~p2-1 之间的字符用 r 替换，然后 p1 指向 p2，node 指向 root
     * p2 指向的当前字符在敏感词库中，p2 已经指向最后一个字符，node 向后移动一位后，判断 node.isEnd ，如果为 true 则将 p1~p2 之间的字符用 r 替换，然后执行 p2++ 跳出循环；否则直接执行 p2++ 跳出循环
     * <p>
     * 对敏感词中加入符号的处理：
     * 如：有敏感词 abc
     * 对文本 ※a※b※c
     * 遇到符号，p2 并没有指向最后一个字符，并且 node 指向 root，则 p1++,p2++
     * 遇到符号，p2 并没有指向最后一个字符，并且 node 没有指向 root，则 p2++
     * 遇到符号，p2 指向最后一个字符，并且 node 没有指向 root，则将 p1~p2-1 之间的字符用 r 替换,并跳出循环
     *
     * <p>
     */
    public String filter(String sentence, char r) {
        if (Objects.isNull(sentence)) {
            return null;
        }
        char[] chars = sentence.toCharArray();
        int p1 = 0;
        int p2 = 0;
        TrieNode node = trie.getRoot();
        while (p2 < chars.length) {
            if (isSymbol(chars[p2]) && p2 != chars.length - 1) {
                if (node == trie.getRoot()) {
                    p1++;
                    p2++;
                    if (p2 >= chars.length) {
                        break;
                    }
                } else {
                    p2++;
                    if (p2 >= chars.length) {
                        break;
                    }
                    continue;
                }
            }
            if (isSymbol(chars[p2]) && p2 == chars.length - 1) {
                if (node != trie.getRoot()) {
                    if (p1 == p2) {
                        replace(chars, r, p1, p2);
                    } else {
                        replace(chars, r, p1, p2 - 1);
                    }
                    break;
                }
            }
            if (node.getNextNodes().containsKey(chars[p2]) && p2 != chars.length - 1) {
                if (!node.isEnd()) {
                    node = node.getNextNodes().get(chars[p2]);
                    p2++;
                } else {
                    if (p1 == p2) {
                        replace(chars, r, p1, p2);
                        p1++;
                        p2++;
                        node = trie.getRoot();
                    } else {
                        replace(chars, r, p1, p2 - 1);
                        p1 = p2;
                        node = trie.getRoot();
                    }
                }

            } else if (node.getNextNodes().containsKey(chars[p2]) && p2 == chars.length - 1) {
                node = node.getNextNodes().get(chars[p2]);
                if (node.isEnd()) {
                    replace(chars, r, p1, p2);
                }
                p2++;
            } else {
                if (node.isEnd()) {
                    if (p1 == p2) {
                        replace(chars, r, p1, p2);
                        p1++;
                        p2++;
                        node = trie.getRoot();
                    } else {
                        replace(chars, r, p1, p2 - 1);
                        p1 = p2;
                        node = trie.getRoot();
                    }
                } else {
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
        }
        return String.valueOf(chars);
    }

}
