## 今日目标：扩展加载器功能，提升灵活性

>代码仓库：[github.com/Eumenides1/…](https://github.com/Eumenides1/diting)

在第一天的基础上，今天我们继续优化谛听（Diting）的加载器功能，进一步提升其灵活性和扩展性。具体来说，我们将完成以下任务：

1.  **扩展 TxtWordLoader**：支持用户自定义分隔符，允许用户通过配置文件指定分隔符类型（如换行符、逗号、分号等）。
1.  **扩展 MySqlWordLoader**：支持用户自定义字段和筛选条件，允许用户通过配置文件指定数据库表的字段名和查询条件。

## 为什么要扩展这些功能？
### **支持自定义分隔符：提升文件加载的灵活性**

#### **问题背景**

在上一节实现的 `TxtWordLoader` 中，我们默认使用换行符作为分隔符来分割敏感词。然而，在实际应用中，用户可能会使用不同的分隔符来存储敏感词库，例如：

-   逗号分隔：`敏感词1,敏感词2,敏感词3`
-   分号分隔：`敏感词1;敏感词2;敏感词3`
-   竖线分隔：`敏感词1|敏感词2|敏感词3`

如果只支持换行符作为分隔符，用户的敏感词库文件格式将受到限制，无法灵活适应不同的需求。
#### **解决方案**

通过扩展 `TxtWordLoader`，我们支持用户自定义分隔符。用户可以在配置文件中指定分隔符类型（如 `COMMA`、`SEMICOLON`、`PIPE` 等），从而灵活地加载不同格式的敏感词库文件。

#### **好处**

-   **灵活性**：用户可以根据自己的需求选择合适的分隔符，无需修改代码。
-   **兼容性**：支持多种常见的分隔符格式，兼容不同的敏感词库文件。
-   **易用性**：通过配置文件即可完成分隔符的设置，降低使用门槛。

### **支持自定义字段和条件：提升数据库加载的灵活性**

#### **问题背景**

在第一天实现的 `MySqlWordLoader` 中，我们默认从数据库表的 `word` 字段加载敏感词。然而，在实际应用中，用户的数据库表结构可能不同，例如：

-   字段名可能不是 `word`，而是 `keyword` 或 `sensitive_word`。
-   可能需要根据某些条件筛选敏感词，例如只加载状态为 `active` 的敏感词。

如果只支持固定的字段名和查询条件，用户的数据库表结构和查询需求将受到限制。

#### **解决方案**

通过扩展 `MySqlWordLoader`，我们支持用户自定义字段名和查询条件。用户可以在配置文件中指定字段名和条件，从而灵活地从数据库中加载敏感词。

#### **好处**

-   **灵活性**：用户可以根据自己的数据库表结构和查询需求，自定义字段名和条件。
-   **扩展性**：支持复杂的查询条件，满足不同场景的需求。
-   **易用性**：通过配置文件即可完成字段和条件的设置，无需修改代码。

----
## 代码详解：扩展功能的设计与实现

### **DitingProperties：配置类的扩展**

为了支持新的功能，我们对 `DitingProperties` 类进行了扩展，增加了分隔符配置和嵌套配置的支持：
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
    private Map<String, Object> config;

    /**
     * 分隔符，仅适用于 TXT 类型，支持常量
     * 默认值为换行符：NEWLINE
     */
    private String delimiter = "NEWLINE";

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(LoaderType loaderType) {
        this.loaderType = loaderType;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * 获取解析后的分隔符值
     */
    public String resolveDelimiter() {
        return Delimiter.getDelimiterValue(delimiter);
    }

    /**
     * 获取配置中的 Map 值
     *
     * @param key 配置键
     * @return 嵌套的 Map 值
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getConfigMap(String key) {
        Object value = config.get(key);
        if (value instanceof Map) {
            return (Map<String, String>) value;
        }
        throw new IllegalArgumentException("Key " + key + " is not a valid Map<String, String> type.");
    }

    /**
     * 加载器类型的枚举
     */
    public enum LoaderType {
        TXT, JSON, MYSQL, REDIS
    }
}
```
-   **delimiter**：新增的分隔符配置项，默认值为 `NEWLINE`（换行符）。
-   **resolveDelimiter**：根据用户配置的分隔符键，解析出实际的分隔符值。
-   **getConfigMap**：支持从配置中获取嵌套的 `Map` 值，用于 MySQL 加载器的字段和条件配置。

### **TxtWordLoader：支持自定义分隔符**

为了支持用户自定义分隔符，我们对 `TxtWordLoader` 进行了扩展：
```java
public class TxtWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtWordLoader.class);

    // 默认分隔符为换行
    private String delimiter = "\\n";
    private String resourcePath;

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        LOGGER.info("Loading sensitive words from TXT file: {} with delimiter: {}", resourcePath, delimiter);
        Set<String> words = new HashSet<>();

        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // 保证所有内容读取
            }
            String[] wordArray = content.toString().split(delimiter);
            for (String word : wordArray) {
                if (!word.trim().isEmpty()) {
                    words.add(word.trim());
                }
            }
        }

        LOGGER.info("Loaded sensitive words: {}", words);
        return words;
    }
}
```
-   **delimiter**：新增的分隔符属性，用户可以通过 `setDelimiter` 方法设置自定义分隔符。
-   **loadSensitiveWords**：根据用户指定的分隔符，将文件内容分割成敏感词列表。

### **Delimiter：分隔符枚举**

为了支持用户选择常用的分隔符，我们定义了一个 `Delimiter` 枚举：
```java
public enum Delimiter {
    NEWLINE("\\n"),
    COMMA(","),
    SEMICOLON(";"),
    PIPE("|");

    private final String value;

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据用户输入的 key 获取分隔符值
     */
    public static String getDelimiterValue(String key) {
        for (Delimiter delimiter : values()) {
            if (delimiter.name().equalsIgnoreCase(key)) {
                return delimiter.getValue();
            }
        }
        throw new IllegalArgumentException("Unsupported delimiter key: " + key);
    }
}
```
假设用户选择从 TXT 文件加载敏感词库，并使用逗号作为分隔符，配置如下, 可通过`delimiter`传递分隔符
```yaml
sensitive-word:
  loader-type: TXT
  config:
    filePath: sensitive_words.txt
    delimiter: COMMA
```
### **MySqlWordLoader：支持自定义字段和动态条件**

为了支持自定义字段和动态条件查询，我们对 `MySqlWordLoader` 进行了扩展：
```java
public class MySqlWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlWordLoader.class);

    private final DataSource dataSource;
    private final String table;
    private final String columns;
    private final Map<String, String> conditions;

    public MySqlWordLoader(DataSource dataSource, DitingProperties properties) {
        this.dataSource = dataSource;
        this.table = properties.getConfig().get("table").toString();
        this.columns = properties.getConfig().getOrDefault("columns", "word").toString();
        this.conditions = properties.getConfigMap("conditions");
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        String sql = buildQuery();
        LOGGER.info("Generated SQL: {}", sql);

        Set<String> words = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // 动态填充条件参数
            if (conditions != null) {
                int paramIndex = 1;
                for (String value : conditions.values()) {
                    statement.setString(paramIndex++, value);
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    words.add(resultSet.getString(1).trim()); // 获取查询结果的第一列
                }
            }
        }

        LOGGER.info("Loaded sensitive words: {}", words);
        return words;
    }

    /**
     * 构建动态查询 SQL
     */
    private String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT ").append(columns)
                .append(" FROM ").append(table);

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            for (String key : conditions.keySet()) {
                query.append(key).append(" = ? AND "); // 参数化条件
            }
            query.setLength(query.length() - 5); // 去掉最后的 " AND "
        }

        return query.toString();
    }
}
```
修改了MySQL敏感词库加载器的构造方法，新增了一些参数：
-   **dataSource**：数据库连接池，用于获取数据库连接。
-   **table**：数据库表名，用户通过配置文件指定。
-   **columns**：查询字段名，用户通过配置文件指定，默认值为 `word`。
-   **conditions**：查询条件，用户通过配置文件指定，支持多个条件。

并提供了拼接sql的方法：
```java
/**
* 构建动态查询 SQL
*/
private String buildQuery() {
    StringBuilder query = new StringBuilder("SELECT ").append(columns)
        .append(" FROM ").append(table);

    if (conditions != null && !conditions.isEmpty()) {
        query.append(" WHERE ");
        for (String key : conditions.keySet()) {
            query.append(key).append(" = ? AND "); // 参数化条件
        }
        query.setLength(query.length() - 5); // 去掉最后的 " AND "
    }

    return query.toString();
 }
```
假设用户选择从 MySQL 数据库加载敏感词库，并自定义字段和条件，配置如下：
```yaml
sensitive-word:
  loader-type: MYSQL
  config:
    table: sensitive_words
    columns: word
    conditions:
      status: active
      category: illegal
```
生成的 SQL 查询语句为：
```sql
SELECT word FROM sensitive_words WHERE status = ? AND category = ?
```
## 总结：扩展功能的设计与价值

通过今天的开发，我们成功扩展了谛听（Diting）的 MySQL 加载器功能，支持用户自定义字段和动态条件查询。这些改进不仅提升了谛听的灵活性，还为用户提供了更多的配置选项，满足不同场景的需求。

### **好处总结**

1.  **灵活性**：用户可以根据自己的数据库表结构和查询需求，自定义字段名和条件。
1.  **扩展性**：支持复杂的查询条件，满足不同场景的需求。
1.  **易用性**：通过配置文件即可完成字段和条件的设置，无需修改代码。

在接下来的开发中，我们将继续优化谛听的功能，支持更多数据源和高级特性，让谛听真正成为敏感词识别领域的“神兽”！

**PS**：敏感词们，谛听正在快速成长，你们准备好了吗？😎