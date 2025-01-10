
# Diting - A Sensitive Word Detection Component

`Diting` 是一个轻量级、可扩展的敏感词识别组件，支持多种方式加载敏感词库（TXT 文件、JSON 文件、MySQL 数据库、Redis），并提供基于 AC 自动机的高效敏感词匹配功能。

在中国神话中，谛听是地藏菩萨座下的神兽，它耳听八方，能辨善恶，能识真伪，是守护正义的象征。我们的项目 谛听（Diting） ，正是以此为灵感，致力于成为敏感数据识别领域的“神兽”！它用“耳朵”聆听每一段文本，用“智慧”辨别每一个敏感词，守护您的数据安全与内容合规。

## 功能特色

- **多数据源支持**：支持多种敏感词加载方式，包括：
  - 本地 TXT 文件
  - JSON 文件
  - MySQL 数据库
  - Redis 缓存
- **内置默认词库**：默认提供 6 万+ 敏感词，支持即插即用。
- **动态扩展**：支持用户自定义词库，在默认词库的基础上扩展。
- **兼容性强**：无需配置即可快速使用，满足大部分场景需求。

---

## 快速开始

### 1. 引入依赖

确保项目使用 Maven 或 Gradle 构建，添加以下依赖：

#### Maven
```xml
<dependency>
  <groupId>io.github.eumenides1</groupId>
  <artifactId>diting-spring-boot-starter</artifactId>
  <version>0.0.3</version>
</dependency>
```
#### Gradle
```groovy
implementation 'io.github.eumenides1:diting-spring-boot-starter:0.0.1'
```

### 2. 最小化使用（无需配置）

组件内置了 6 万+ 敏感词的默认词库，用户无需任何配置即可直接使用：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensitiveWordDemo {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    public void demo() {
        String input = "这是一段包含敏感词word1和word2的文本。";
        List<String> sensitiveWords = sensitiveWordService.containsSensitiveWord(input);
        String replacedText = sensitiveWordService.replaceSensitiveWords(input);

        System.out.println("识别到的敏感词：" + sensitiveWords);
        System.out.println("脱敏处理后的文本：" + replacedText);
    }
}
```

输出示例：
```
识别到的敏感词：[word1, word2]
脱敏处理后的文本：这是一段包含敏感词****和****的文本。
```

---

## 配置使用

组件支持根据配置切换敏感词数据源，以下是常见的配置示例。

### 1. 使用本地 TXT 文件

在 `application.yml` 中配置：

```yaml
sensitive-word:
  loader-type: TXT
  config:
    filePath: sensitive_words.txt
    delimiter: COMMA # 可选，默认换行分隔（NEWLINE）
```

将敏感词文件 `sensitive_words.txt` 放置在项目的 `resources` 目录下，内容示例：

```
word1,word2,word3
```

---

### 2. 使用 JSON 文件

在 `application.yml` 中配置：

```yaml
sensitive-word:
  loader-type: JSON
  config:
    filePath: sensitive_words.json
```

将敏感词文件 `sensitive_words.json` 放置在 `resources` 目录下，内容示例：

```json
["word1", "word2", "word3"]
```

---

### 3. 使用 MySQL 数据库

在 `application.yml` 中配置：

```yaml
sensitive-word:
  loader-type: MYSQL
  config:
    table: sensitive_words
    columns: word
    conditions:
      status: active
```

组件会从 `sensitive_words` 表中加载 `status` 为 `active` 的敏感词。

---

### 4. 使用 Redis

在 `application.yml` 中配置：

```yaml
sensitive-word:
  loader-type: REDIS
  config:
    key: sensitive_words_key
```

Redis 中的 `sensitive_words_key` 键对应一个敏感词列表。

---

## 高级功能

### 1. 动态扩展默认词库

在 `application.yml` 中开启默认词库兼容模式：

```yaml
sensitive-word:
  loader-type: TXT
  include-default: true # 启用默认词库
  config:
    filePath: custom_sensitive_words.txt
```

用户可以在默认词库的基础上添加自定义词库。

---

## 常见问题

### 1. 如何查看加载的敏感词？
可以通过日志输出确认加载器类型和加载的敏感词库路径。

### 2. 如何扩展新的加载器？
可实现 `SensitiveWordLoader` 接口，并通过自定义 `@Bean` 的方式注入新的加载器。

---

## 贡献与支持

欢迎提交 Issue 或 PR，帮助我们改进组件功能。

GitHub 地址：[谛听敏感词识别组件](https://github.com/Eumenides1/diting)
