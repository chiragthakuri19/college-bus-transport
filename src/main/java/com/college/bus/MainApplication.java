package com.college.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {
    "com.college.bus",
    "com.college.bus.config",
    "com.college.bus.controller",
    "com.college.bus.service",
    "com.college.bus.security",
    "com.college.bus.repository"
})
@EntityScan(basePackages = "com.college.bus.model")
@EnableJpaRepositories(basePackages = "com.college.bus.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.college.bus",
    "com.college.bus.config",
    "com.college.bus.controller",
    "com.college.bus.service",
    "com.college.bus.security",
    "com.college.bus.repository"
})
public class MainApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(MainApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
} 