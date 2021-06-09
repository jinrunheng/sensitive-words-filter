package com.duby.util.TrieFilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrieFilterTest {
    @Test
    public void testTrieFilterInitMethod(){
        TrieFilter trieFilter = new TrieFilter();
        Assertions.assertFalse(trieFilter.getTrie().exist("性爱电影"));
        trieFilter.init();
        Assertions.assertTrue(trieFilter.getTrie().exist("性爱电影"));
    }

    @Test
    public void testTrieFilterBatchAddMethod(){
        TrieFilter trieFilter = new TrieFilter();
        Assertions.assertFalse(trieFilter.exist("测试敏感词1"));
        trieFilter.batchAdd("test.txt");
        Assertions.assertTrue(trieFilter.exist("测试敏感词1"));
    }
}