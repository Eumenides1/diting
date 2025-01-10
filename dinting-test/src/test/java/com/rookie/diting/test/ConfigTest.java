package com.rookie.diting.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Name：ConfigTest
 * Author：eumenides
 * Created on: 2025/1/9
 * Description:
 */
@SpringBootTest
@ContextConfiguration(classes = DitingApplication.class)
public class ConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testDataSource() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}