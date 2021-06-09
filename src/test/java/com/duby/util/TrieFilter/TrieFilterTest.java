package com.duby.util.TrieFilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrieFilterTest {
    @Test
    public void testTrieFilterInitMethod() {
        TrieFilter trieFilter = new TrieFilter();
        Assertions.assertFalse(trieFilter.getTrie().exist("性爱电影"));
        trieFilter.init();
        Assertions.assertTrue(trieFilter.getTrie().exist("性爱电影"));
    }

    @Test
    public void testTrieFilterBatchAddMethod() {
        TrieFilter trieFilter = new TrieFilter();
        Assertions.assertFalse(trieFilter.exist("测试敏感词1"));
        trieFilter.batchAdd("test.txt");
        Assertions.assertTrue(trieFilter.exist("测试敏感词1"));
    }

    @Test
    public void testReplaceMethod() {
        char[] chars = new char[]{'c', 'a', 'b', 'd'};
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.replace(chars, '*', 1, 2);
        Assertions.assertEquals(chars[1], '*');
        Assertions.assertEquals(chars[2], '*');
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
        Assertions.assertEquals(filteredSentence,"xwa********");
    }

    @Test
    public void test(){
        TrieFilter trieFilter = new TrieFilter();
        trieFilter.put("abc");
        TrieNode a = trieFilter.getTrie().getRoot().getNextNodes().get('a');
    }
}