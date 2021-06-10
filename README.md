# sensitive-words-filter

这是一个使用 Java 语言编写的一个敏感词过滤器

### 敏感词库

敏感词库拷贝自：https://github.com/lyy720301/Sensitive-word





### 项目原理

前缀树(Trie)

![trie](https://github.com/jinrunheng/my-img/blob/main/img/trie.png)

### 用法

#### 初始化

```java
TrieFilter trieFilter = new TrieFilter();
```


#### 加载敏感词库文件

文件格式要求，每个敏感词占一行
```text
aaa
bbb
ccc
...
```

```java
trieFilter.batchAdd(String filePath);
```
trieFilter 支持添加，删除敏感词,以及判断某个敏感词是否在当前构建的敏感词字典中
```java
@Test
public void testTrieFilterBasicMethod(){
    TrieFilter trieFilter = new TrieFilter();
    Assertions.assertFalse(trieFilter.exist("test"));
    trieFilter.put("test");
    Assertions.assertTrue(trieFilter.exist("test"));
    trieFilter.remove("test");
    Assertions.assertFalse(trieFilter.exist("test"));
}
```
#### 过滤敏感词
```java
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
```
敏感词过滤器可以有效屏蔽干扰符号
```java
@Test
public void testSentenceWithSymbol() {
    TrieFilter trieFilter = new TrieFilter();
    trieFilter.put("abcabc");
    String sentence = "^a^^^b/c&ab&&c^";
    String filteredSentence = trieFilter.filter(sentence, '*');
    Assertions.assertEquals(filteredSentence, "^*************^");
}
```
### 版权信息
[Apache License 2.0](https://github.com/jinrunheng/sensitive-words-filter/blob/main/LICENSE)











