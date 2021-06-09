package com.duby.util.TrieFilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    public void testTrieBasicMethod(){
        Trie trie = new Trie();
        trie.put("abc");
        trie.put("abc");
        trie.put("ad");
        trie.put("我爱你");
        trie.put("我 爱 你");
        Assertions.assertTrue(trie.exist("abc"));
        trie.remove("abc");
        Assertions.assertFalse(trie.exist("abc"));
        Assertions.assertFalse(trie.exist("我爱"));
        Assertions.assertTrue(trie.exist("我爱你"));
        Assertions.assertTrue(trie.exist("我 爱 你"));
    }
}