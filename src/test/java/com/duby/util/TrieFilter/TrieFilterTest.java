package com.duby.util.TrieFilter;

import com.duby.util.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

class TrieFilterTest {


    @Test
    public void testTrieFilterBatchAddMethod() {
        TrieFilter trieFilter = new TrieFilter();
        Assertions.assertFalse(trieFilter.exist("测试敏感词1"));
        trieFilter.batchAdd("test.txt");
        Assertions.assertTrue(trieFilter.exist("测试敏感词1"));
    }

    @Test
    public void testInitDefaultDataMethod() {
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.batchAdd("sensi_words.txt");
        Assertions.assertTrue(trieFilter.exist("学chao"));
        Assertions.assertTrue(trieFilter.exist("迷藥"));
    }

    @Test
    public void testReplaceMethod() {
        char[] chars = new char[]{'c', 'a', 'b', 'd'};
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.replace(chars, '*', 1, 2);
        Assertions.assertEquals(chars[1], '*');
        Assertions.assertEquals(chars[2], '*');
    }

    /**
     * complexTest 并不是一个稳定的测试
     * 如果有敏感词：
     * 强奸
     * 强奸妇女
     * 文字为：强奸妇女哈哈哈
     * 那么该过滤器的过滤结果为：**妇女哈哈哈
     */
//    @Test
//    public void complexTest() {
//        TrieFilter trieFilter = new TrieFilter();
//        // sensi_words.txt 共有 2479 行
//        trieFilter.batchAdd("sensi_words.txt");
//        trieFilter.put("abcabc");
//        StringBuilder sentence = new StringBuilder();
//        sentence.append("test");
//        BufferedReader reader = FileReader.readFile("sensi_words.txt");
//        int random = new Random().nextInt(2479);
//        int i = 0;
//        int randomTextLen = 0;
//        try {
//            for (String line = reader.readLine(); line != null; line = reader.readLine(), i++) {
//                if (i == random) {
//                    sentence.append(line);
//                    randomTextLen = line.length();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sentence.append("^a^^^b/c&ab&&c");
//        sentence.append("test");
//
//        String filteredSentence = trieFilter.filter(sentence.toString(), '*');
//        StringBuilder actual = new StringBuilder();
//        actual.append("test");
//        for (int j = 0; j < randomTextLen; j++) {
//            actual.append("*");
//        }
//        actual.append("**************");
//        actual.append("test");
//        Assertions.assertEquals(filteredSentence, actual.toString());
//
//    }

    @Test
    public void testOneWord() {
        TrieFilter trieFilter = new TrieFilter();
        String sentence = "a";
        trieFilter.put("a");
        String filteredSentence = trieFilter.filter(sentence, '*');
        Assertions.assertEquals(filteredSentence, "*");
    }

    @Test
    public void testFilterMethod() {
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.put("abc");
        trieFilter.put("bf");
        trieFilter.put("be");
        trieFilter.put("faf");
        String sentence = "xwabfabcfaf";
        String filteredSentence = trieFilter.filter(sentence, '*');
        Assertions.assertEquals(filteredSentence, "xwa********");
    }

    @Test
    public void testSentenceWithSymbol() {
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.put("abcabc");
        String sentence = "^a^^^b/c&ab&&c";
        String filteredSentence = trieFilter.filter(sentence, '*');
        Assertions.assertEquals(filteredSentence, "^*************");
    }
}