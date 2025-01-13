---
theme: juejin
---
## 今日目标：支持多种方式读取敏感词库
> 代码仓库：https://github.com/Eumenides1/diting

今天是谛听（Diting）项目的第一天，我们的目标是完成敏感词库加载的基础功能。具体来说，我们要实现以下目标：

1.  **支持多种敏感词库加载方式**：包括从 **TXT文件**、**JSON文件**、**MySQL** 和 **Redis** 中加载敏感词。
1.  **设计灵活的加载器接口**：方便未来扩展更多数据源。
1.  **初步测试加载功能**：确保每种加载方式都能正确读取敏感词。

听起来是不是很酷？但在实现这个功能的过程中，我们面临了一些挑战。接下来，我将带你一起分析这些挑战，并探讨如何解决它们。
## 挑战一：如何灵活支持多种数据源？
我们需要支持从多种数据源加载敏感词库，比如：

-   **文件**：TXT、JSON 等。
-   **数据库**：MySQL、PostgreSQL 等。
-   **缓存**：Redis、Memcached 等。

每种数据源的读取方式完全不同，如何设计一个灵活的架构，让用户可以根据自己的需求选择合适的数据源？
### **解决方案**
-   **设计统一的加载器接口**：定义一个 `WordLoader` 接口，所有数据源的加载器都实现这个接口。
-   **通过配置文件动态选择加载器**：用户可以在 `application.yml` 中配置使用哪种数据源，Starter 根据配置自动加载对应的实现类。
-   **支持扩展**：如果用户有自定义的数据源需求，可以通过实现 `WordLoader` 接口来扩展

## 挑战二：如何获取用户侧的数据库、Redis 等资源？

如果用户选择从 **MySQL** 或 **Redis** 加载敏感词库，我们需要获取用户侧的数据库连接信息（如 URL、用户名、密码）或 Redis 连接信息（如 host、port、key）。这些信息通常由用户在 `application.yml` 中配置。

### **解决方案**

-   **使用 Spring Boot 的配置绑定**：通过 `@ConfigurationProperties` 注解，将用户的配置绑定到 Starter 的属性类中。
-   **动态创建数据源**：根据用户的配置，动态创建数据库连接或 Redis 客户端。
-   **依赖注入**：如果用户已经配置了数据源（如 `DataSource` 或 `RedisTemplate`），可以直接注入使用，避免重复配置。

##  挑战三：如何保证 Starter 的易用性？
作为一个 Starter，我们的目标是让用户能够快速集成敏感词检测功能，而不需要关心底层实现细节。如何让 Starter 的配置尽可能简单，同时又不失灵活性？

### **解决方案**

-   **提供默认配置**：为每种数据源提供默认配置，用户只需配置必要的信息（如文件路径、数据库连接信息等）。
-   **自动配置**：通过 `spring.factories` 和 `@ConditionalOnProperty` 注解，实现 Starter 的自动配置。
-   **友好的错误提示**：如果用户配置有误，提供清晰的错误提示，帮助用户快速定位问题。

虽然实现一个支持多种数据源的 Starter 面临诸多挑战，但每一个挑战都对应着一个优化点。通过灵活的设计、自动化的配置和性能优化，我们可以让谛听（Diting）成为一个强大而易用的敏感词检测工具。

在接下来的开发中，我们将逐步解决这些挑战，让谛听（Diting）真正成为敏感词识别领域的“神兽”！

----
## 代码结构：初窥谛听的“五脏六腑”
在开始写代码之前，我们先来看看今天的代码结构。为了方便理解，我把代码分成了几个部分：
```
dinting-test/
├── src/
│   └── ... (测试代码)
├── pom.xml

diting-core/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── rookie/
│                   └── diting/
│                       ├── loader/impl/  # 敏感词库加载器的具体实现
│                       ├── service/      # 核心服务逻辑
│                       └── ... (其他核心代码)
├── pom.xml

diting-spring-boot-starter/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── rookie/
│       │           └── diting/
│       │               ├── config/       # Spring Boot 自动配置
│       │               │   ├── DitingAutoConfiguration.java
│       │               │   └── DitingProperties.java
│       │               └── loader/impl/  # 加载器的具体实现
│       │                   ├── JsonWordLoader.java
│       │                   ├── MySqlWordLoader.java
│       │                   ├── RedisWordLoader.java
│       │                   └── TxtWordLoader.java
│       └── resources/
│           └── META-INF/
│               └── spring.factories      # Spring Boot 自动配置入口
└── pom.xml
```
是不是一目了然？接下来，我们逐一来看看每个部分的代码和逻辑。

## 代码详解
### 从加载器接口到具体实现
####  **加载器接口设计**

首先，我们需要定义一个加载器接口，方便未来扩展更多数据源。以下是 `SensitiveWordLoader` 接口的设计：
```java
/**
 * Name：SensitiveWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: 敏感词加载接口
 */
public interface SensitiveWordLoader {
    Set<String> loadSensitiveWords() throws Exception;
}
```
这个接口非常简单，只有一个 `loadSensitiveWords` 方法，用于返回敏感词列表。未来无论是从文件、数据库还是其他数据源加载敏感词，都可以实现这个接口。
#### **具体加载器实现**
接下来，我们来看几种具体的加载器实现。
##### **TxtWordLoader：从TXT文件加载敏感词**
```java
public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(TxtWordLoader.class.getName());
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from TXT file: " + resourcePath);
        Set<String> words = new HashSet<>();

        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        }

        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
```
整体的实现思路其实很简单，从指定的文件中读取出敏感词，并塞入到一个set中去，这里选择set是因为敏感词库中可能存在重复的敏感词，使用 `Set` 集合可以自动去重，确保每个敏感词只出现一次；而且`Set` 集合基于哈希表实现，查找时间复杂度为 O(1)，适合用于敏感词检测场景。
##### **JsonWordLoader：从JSON文件加载敏感词**
```java
public class JsonWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(JsonWordLoader.class.getName());
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from JSON file: " + resourcePath);
        ClassPathResource resource = new ClassPathResource(resourcePath);
        ObjectMapper mapper = new ObjectMapper();
        Set<String> words = new HashSet<>(mapper.readValue(resource.getInputStream(), Set.class));
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
```
这个加载器使用 `Jackson` 库从JSON文件中读取敏感词列表。JSON文件的格式应该是一个字符串数组，例如：
```json
["敏感词1", "敏感词2", "敏感词3"]
```
##### **MySqlWordLoader：从MySQL数据库加载敏感词**
```java
public class MySqlWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(MySqlWordLoader.class.getName());
    private final DataSource dataSource;
    private final String tableName;

    public MySqlWordLoader(DataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from MySQL table: " + tableName);
        Set<String> words = new HashSet<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT word FROM " + tableName)) {

            while (resultSet.next()) {
                words.add(resultSet.getString("word"));
            }
        }

        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
```
这个加载器从MySQL数据库中读取敏感词。假设数据库中有一个 `sensitive_words` 表，其中包含一个 `word` 字段。后续我们逐步优化，扩展这个loader的功能。
##### **RedisWordLoader：从Redis加载敏感词**
```java
public class RedisWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = Logger.getLogger(RedisWordLoader.class.getName());
    private final RedisTemplate<String, String> redisTemplate;
    private final String key;

    public RedisWordLoader(RedisTemplate<String, String> redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from Redis key: " + key);
        Set<String> words = new HashSet<>(redisTemplate.opsForSet().members(key));
        LOGGER.info("Loaded sensitive words: " + words);
        return words;
    }
}
```
这个加载器从Redis中读取敏感词。假设敏感词存储在一个Redis列表中。

### 自动配置与加载器的实现
谛听（Diting）的自动配置功能。通过 `DitingAutoConfiguration` 类，我们可以根据用户的配置动态创建对应的敏感词加载器（如 `TxtWordLoader`、`JsonWordLoader` 等），并将其注入到 Spring 容器中。
#### **DitingProperties：配置绑定类**
```java
@ConfigurationProperties(prefix = "sensitive-word")
@Validated
public class DitingProperties {

    /**
     * 加载器类型 (TXT, JSON, MYSQL, REDIS)
     */
    @NotNull(message = "Loader type must be specified.")
    private LoaderType loaderType;

    /**
     * 配置参数 (根据 loaderType 的类型动态校验)
     */
    @NotNull(message = "Config must not be null.")
    private Map<String, String> config;

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(LoaderType loaderType) {
        this.loaderType = loaderType;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    /**
     * 加载器类型的枚举
     */
    public enum LoaderType {
        TXT, JSON, MYSQL, REDIS
    }
}
```
-   **@ConfigurationProperties**：将 `application.yml` 中的 `sensitive-word` 配置项绑定到该类。
-   **@Validated**：启用参数校验，确保 `loaderType` 和 `config` 不为空。
-   **LoaderType**：枚举类型，定义了支持的加载器类型（TXT、JSON、MYSQL、REDIS）。
-   **config**：一个 `Map`，用于存储不同加载器所需的配置参数（如 `filePath`、`table`、`key` 等）
#### **DitingAutoConfiguration：自动配置类**
```java
@Configuration
@EnableConfigurationProperties(DitingProperties.class)
public class DitingAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DitingAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "TXT")
    public SensitiveWordLoader txtWordLoader(DitingProperties properties) {
        validateConfig(properties, "filePath");
        String filePath = properties.getConfig().get("filePath");
        LOGGER.info("Configuring TxtWordLoader with file path: {}", filePath);

        TxtWordLoader txtLoader = new TxtWordLoader();
        txtLoader.setResourcePath(filePath);
        return txtLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "JSON")
    public SensitiveWordLoader jsonWordLoader(DitingProperties properties) {
        validateConfig(properties, "filePath");
        String filePath = properties.getConfig().get("filePath");
        LOGGER.info("Configuring JsonWordLoader with file path: {}", filePath);

        JsonWordLoader jsonLoader = new JsonWordLoader();
        jsonLoader.setResourcePath(filePath);
        return jsonLoader;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "MYSQL")
    public SensitiveWordLoader mysqlWordLoader(DataSource dataSource, DitingProperties properties) {
        validateConfig(properties, "table");
        String table = properties.getConfig().get("table");
        LOGGER.info("Configuring MySqlWordLoader with table: {}", table);

        return new MySqlWordLoader(dataSource, table);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "sensitive-word.loader-type", havingValue = "REDIS")
    public SensitiveWordLoader redisWordLoader(RedisTemplate<String, String> redisTemplate, DitingProperties properties) {
        validateConfig(properties, "key");
        String key = properties.getConfig().get("key");
        LOGGER.info("Configuring RedisWordLoader with key: {}", key);

        return new RedisWordLoader(redisTemplate, key);
    }

    @Bean
    @ConditionalOnMissingBean
    public SensitiveWordChecker sensitiveWordChecker(SensitiveWordLoader loader) throws Exception {
        return new SensitiveWordChecker(loader);
    }

    private void validateConfig(DitingProperties properties, String requiredKey) {
        if (!properties.getConfig().containsKey(requiredKey)) {
            throw new IllegalArgumentException(
                    "Missing required config key '" + requiredKey + "' for loader type " + properties.getLoaderType());
        }
    }
}
```
-   **@Configuration**：标记该类为配置类。
-   **@EnableConfigurationProperties**：启用 `DitingProperties` 的配置绑定。
-   **@ConditionalOnProperty**：根据 `sensitive-word.loader-type` 的值，动态创建对应的加载器。
-   **@ConditionalOnMissingBean**：确保只有当用户没有自定义实现时，才创建默认的 Bean。
-   **validateConfig**：校验配置参数是否完整，如果缺少必要参数，抛出异常。

## 展望

谛听（Diting）项目的第一天是一个良好的开端。通过今天的开发，我们不仅实现了敏感词库加载的基础功能，还为未来的扩展和优化奠定了坚实的基础。在接下来的开发中，我们将继续完善谛听的功能，支持更多数据源和高级特性，让谛听真正成为敏感词识别领域的“神兽”！

**PS**：敏感词们，谛听正在快速成长，你们准备好了吗？😎


