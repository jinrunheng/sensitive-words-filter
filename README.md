# sensitive-words-filter
This is a sensitive words filter implemented in Java

### 敏感词库

默认敏感词库拷贝自：https://github.com/lyy720301/Sensitive-word

项目参考：https://github.com/elulis/sensitive-words

----- 删-----

用法：

```java
// 使用默认单例（加载默认敏感词库）
SensitiveFilter filter = SensitiveFilter.DEFAULT;
// 向过滤器增加一个词
filter.put("婚礼上唱春天在哪里");
	
// 待过滤的句子
String sentence = "然后，市长在婚礼上唱春天在哪里。";
// 进行过滤
String filted = filter.filter(sentence, '*');
	
// 如果未过滤，则返回输入的String引用
if(sentence != filted){
	// 句子中有敏感词
	System.out.println(filted);
}
```

打印结果：

```text
然后，**在*********。
```











