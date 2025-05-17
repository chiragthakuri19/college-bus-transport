package com.college.bus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Configuration
@EnableConfigurationProperties
@EntityScan(basePackages = "com.college.bus.model")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("spring.profiles.active", "prod");
                servletContext.setInitParameter("server.servlet.context-path", "/");
                servletContext.setInitParameter("spring.jpa.open-in-view", "false");
            }
        };
    }
} 