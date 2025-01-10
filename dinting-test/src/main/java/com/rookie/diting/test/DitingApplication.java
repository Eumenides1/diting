package com.rookie.diting.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Name：DitingApplication
 * Author：eumenides
 * Created on: 2025/1/8
 * Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.rookie.diting")
public class DitingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DitingApplication.class, args);
    }
}
