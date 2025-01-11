package com.rookie.diting.loader.impl;

import com.rookie.diting.config.DitingProperties;
import com.rookie.diting.loader.SensitiveWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Name：MySqlWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: MySQL 数据库加载器，用于从数据库中加载敏感词。
 */
public class MySqlWordLoader implements SensitiveWordLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlWordLoader.class);

    private final DataSource dataSource;
    private final String table;
    private final String columns;
    private final Map<String, String> conditions;

    /**
     * 修改后的构造函数，接收单独的参数
     *
     * @param dataSource 数据源
     * @param table      数据表名
     * @param columns    需要查询的列
     * @param conditions 查询条件
     */
    public MySqlWordLoader(DataSource dataSource, String table, String columns, Map<String, String> conditions) {
        this.dataSource = dataSource;
        this.table = table;
        this.columns = columns;
        this.conditions = conditions != null ? new HashMap<>(conditions) : Collections.emptyMap();
    }

    @Override
    public Set<String> loadSensitiveWords() throws Exception {
        String sql = buildQuery();
        LOGGER.info("Generated SQL: {}", sql);
        Set<String> words = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // 动态填充条件参数
            if (conditions != null && !conditions.isEmpty()) {
                int paramIndex = 1;
                for (String value : conditions.values()) {
                    statement.setString(paramIndex++, value);
                }
            }
            LOGGER.debug("Executing statement: {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String word = resultSet.getString(1).trim(); // 获取查询结果的第一列
                    words.add(word);
                }
            }
        }
        LOGGER.info("Loaded {} sensitive words from MySQL.", words.size());
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
