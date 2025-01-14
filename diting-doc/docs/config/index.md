# 谛听配置解析
本文档详细介绍了如何配置和使用谛听敏感词过滤组件的各个功能模块。通过配置文件，您可以灵活地启用或禁用不同的敏感词加载方式，并自定义相关参数。
## 配置文件结构
敏感词过滤组件的配置通过 sensitive-word 前缀进行管理。以下是配置文件的完整结构：
```yaml
sensitive-word:
  loaders:
    txt:
      enabled: true
      filePath:
        - /path/to/words1.txt
        - /path/to/words2.txt
      delimiter: "\n"
    json:
      enabled: false
      filePath: /path/to/words.json
    mysql:
      enabled: false
      table: sensitive_words
      columns: word
      conditions:
        type: "all"
    redis:
      enabled: false
      key: sensitive_words
  default-loader:
    enabled: true
  console-enabled: true
  enabled-types:
    - all
```
### 配置项说明
#### loaders
`loaders` 部分用于配置敏感词库的加载方式。支持以下四种加载方式：
- `txt`: 从文本文件加载敏感词。
  - `enabled`: 是否启用该加载方式，默认为 `true`。
  - `filePath`: 敏感词文件的路径，支持多个文件路径。
  - `delimiter`: 敏感词之间的分隔符，默认为换行符`\n`。
- `json`:
  - `enabled`: 是否启用该加载方式，默认为 `false`。
  - filePath: JSON 文件的路径。
- `mysql`: 从 MySQL 数据库加载敏感词。
  - `enabled`: 是否启用该加载方式，默认为 `false`。
  - `table`: 数据库表名。
  - `columns`: 敏感词所在的列名。 
  - `conditions`: 查询条件，以键值对形式提供。
- `redis`: 从 Redis 加载敏感词。
  - `enabled`: 是否启用该加载方式，默认为 `false`。 
  - `key`: Redis 中存储敏感词的键名。

#### default-loader
- `default-loader` 用于配置是否启用默认的敏感词库加载。
  - `enabled`: 是否启用默认加载器，默认为 `true`。

#### console-enabled
- `console-enabled` 用于配置是否启用控制台页面。
  - `enabled`: 是否启用控制台页面，默认为 `true`。

#### enabled-types
- `enabled-types` 用于配置用户选择的敏感词类型，支持一种或多种类型，默认为 `all`。
  - `enabled-types`: 敏感词类型列表，默认为 ["all"]。