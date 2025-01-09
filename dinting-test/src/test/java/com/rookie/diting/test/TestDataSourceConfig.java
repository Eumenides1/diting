package com.rookie.diting.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Name：TestDataSourceConfig
 * Author：eumenides
 * Created on: 2025/1/9
 * Description:
 */
@TestConfiguration
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://gz-cynosdbmysql-grp-ohj017lr.sql.tencentcdb.com:23186/sensitive_db");
        dataSource.setUsername("root");
        dataSource.setPassword("8uhb^YJm");
        return dataSource;
    }
}
