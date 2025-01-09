package com.rookie.diting.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Name：ConfigTest
 * Author：eumenides
 * Created on: 2025/1/9
 * Description:
 */
@SpringBootTest
public class ConfigTest {

    @Value("${spring.datasource.url:}")
    private String datasourceUrl;

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    public void testConfigLoading() {
        System.out.println("Datasource URL: " + datasourceUrl);
        assertNotNull(datasourceUrl);
    }

    @Test
    public void printConditionEvaluationReport() {
        ConditionEvaluationReport report = context.getBean(ConditionEvaluationReport.class);
        System.out.println(report);
    }
}