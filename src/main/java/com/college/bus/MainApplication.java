package com.college.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.college.bus.model")
@EnableJpaRepositories(basePackages = "com.college.bus.repository")
@EnableTransactionManagement
public class MainApplication {
    public static void main(String[] args) {
        try {
            SpringApplication app = new SpringApplication(MainApplication.class);
            app.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 