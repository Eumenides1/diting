package com.rookie.diting.loader.impl;

import com.rookie.diting.config.DitingProperties;
import com.rookie.diting.core.ac.ACTrie;
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

    public MySqlWordLoader(DataSource dataSource, DitingProperties properties) {
        this.dataSource = dataSource;
        this.table = properties.getConfig().get("table").toString();
        this.columns = properties.getConfig().getOrDefault("columns", "word").toString();
        this.conditions = properties.getConfigMap("conditions");
    }

    @Override
    public List<String> loadSensitiveWords() throws Exception {
        String sql = buildQuery();
        LOGGER.info("Generated SQL: {}", sql);
        List<String> words = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // 动态填充条件参数
            if (conditions != null) {
                int paramIndex = 1;
                for (String value : conditions.values()) {
                    statement.setString(paramIndex++, value);
                }
            }
            LOGGER.info("Generated statement: {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String word = resultSet.getString(1).trim();// 获取查询结果的第一列
                    words.add(word);
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
