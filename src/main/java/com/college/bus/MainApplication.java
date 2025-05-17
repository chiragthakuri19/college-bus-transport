package com.college.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.college.bus")
@EntityScan("com.college.bus.model")
@EnableJpaRepositories("com.college.bus.repository")
@EnableTransactionManagement
public class MainApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(MainApplication.class, args);
        } catch (Exception e) {
            logger.error("Application failed to start", e);
            System.exit(1);
        }
    }
} 