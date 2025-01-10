
# Diting - A Sensitive Word Detection Component

`Diting` 是一个轻量级、可扩展的敏感词识别组件，支持多种方式加载敏感词库（TXT 文件、JSON 文件、MySQL 数据库、Redis），并提供基于 AC 自动机的高效敏感词匹配功能。

## 功能特点

- **多方式加载敏感词库**：
  - 支持从 TXT 文件加载敏感词。
  - 支持从 JSON 文件加载敏感词。
  - 支持从 MySQL 数据库加载敏感词。
  - 支持从 Redis 加载敏感词。
- **基于 AC 自动机的高效匹配**：快速检测文本中是否包含敏感词。
- **动态扩展**：通过配置文件快速切换敏感词加载方式。
- **Spring Boot 支持**：通过 `Spring Boot Starter` 快速集成。

## 快速开始

### 1. 添加 Maven 依赖

在您的项目的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.rookie</groupId>
    <artifactId>diting-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. 配置文件示例

根据您的需求在 `application.yml` 中添加配置：

#### TXT 文件加载

- 简单配置 
```yaml
sensitive-word:
  loader-type: TXT
  config:
    filePath: sensitive_words.txt
```

- 支持配置分隔符,默认以换行符分割
```yaml
sensitive-word:
  loader-type: TXT
  config:
    filePath: sensitive_words.txt
    delimiter: COMMA
```


#### JSON 文件加载

```yaml
sensitive-word:
  loader-type: JSON
  config:
    filePath: sensitive_words.json
```

#### MySQL 数据库加载

- 简单数据库配置，默认读取字段名为`word`

```yaml
sensitive-word:
  loader-type: MYSQL
  config:
    table: sensitive_words

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sensitive_db
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- 支持复杂配置,可配置表名，字段，过滤条件等
```yaml
sensitive-word:
  loader-type: MYSQL
  config:
    table: sensitive_words
    columns: word
    conditions:
      is_active: "1"
      type: "prohibited"
```

#### Redis 加载

```yaml
sensitive-word:
  loader-type: REDIS
  config:
    key: sensitive_words

spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password: your_password
```

### 3. 在代码中使用

您可以通过 `SensitiveWordChecker` 检测文本是否包含敏感词：

```java
import com.diting.service.SensitiveWordChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensitiveWordService {

    @Autowired
    private SensitiveWordChecker checker;

    public boolean containsSensitiveWords(String text) {
        return checker.check(text);
    }
}
```

---

## 系统要求

- **Java 版本**：17+
- **Spring Boot 版本**：3.2.6+
- **依赖管理工具**：Maven

---

## 开发者指南

### 本地开发

#### 克隆项目

```bash
git clone https://github.com/Eumenides1/diting.git
cd diting
```

#### 编译和测试

```bash
mvn clean install
```

#### 运行单元测试

```bash
mvn test
```

---

## 贡献指南

我们欢迎任何形式的贡献！以下是参与项目的一些方式：

1. 提交 issue 或建议新功能。
2. 提交代码贡献（Fork 项目并提交 Pull Request）。
3. 提供文档改进。

---

## 许可证

本项目采用 [MIT 许可证](LICENSE)。

---

如果您有任何问题或建议，请随时联系我们！
