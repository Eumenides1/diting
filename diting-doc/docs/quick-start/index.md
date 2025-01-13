# 欢迎使用 "谛听" 项目

"谛听" 是一个轻量级的敏感数据识别组件，旨在为各种应用场景提供高效、灵活的敏感词识别能力。本文档将帮助你快速入门并开始使用这个组件。

## 快速开始

### 安装

首先，你需要在你的 Spring Boot 项目中添加 "谛听" 组件。你可以通过 Maven 进行安装：

```yaml
<dependency>
    <groupId>io.github.eumenides1</groupId>
    <artifactId>diting-spring-boot-starter</artifactId>
    <version>0.0.7</version>
</dependency>
```
### 使用
谛听支持即拆即用的快速开发，无需任何配置就可以在代码中直接使用默认敏感词库。
```java
@SpringBootTest
public class SensitiveWordServiceTest {
    @Test
    public void testReplaceSensitiveWords() {
        SensitiveWordResult result = DitingUtil.replaceSensitiveWords("这是一段包含敏感词做户口本和word2的", DesensitizationType.PARTIAL_REPLACEMENT);
        System.out.println(result);
    }
    @Test
    public void testSensitiveWordServiceList() {
        SensitiveWordResult result = DitingUtil.containsSensitiveWord("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }
    @Test
    public void testFindSensitiveWords() {
        SensitiveWordResult result = DitingUtil.getSensitiveWords("这是一段包含敏感词word1和word2的");
        System.out.println(result);
    }
}
```
**DitingUtil 方法说明**
- `SensitiveWordResult containsSensitiveWord(String text)` 检查文本是否包含敏感词。
- `SensitiveWordResult getSensitiveWords(String text)` 获取文本中的敏感词集合。
- `SensitiveWordResult replaceSensitiveWords(String text, DesensitizationType desensitizationType, ReplacementType replacementChar)`替换文本中的敏感词，使用指定的替换字符。
