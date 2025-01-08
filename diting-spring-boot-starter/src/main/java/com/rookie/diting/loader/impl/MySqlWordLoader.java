package com.rookie.diting.loader.impl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Name：MySqlWordLoader
 * Author：eumenides
 * Created on: 2025/1/8
 * Description: MySQL 数据库加载器，用于从数据库中加载敏感词。
 */
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
