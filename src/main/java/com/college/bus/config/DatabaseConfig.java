package com.college.bus.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Autowired
    private Environment environment;

    @Value("${DATABASE_URL:#{null}}")
    private String databaseUrl;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        HikariConfig config = new HikariConfig();
        
        // Check if we're in production environment
        boolean isProduction = environment.matchesProfiles("prod");
        
        if (isProduction && StringUtils.hasText(databaseUrl)) {
            try {
                // Parse Render's DATABASE_URL
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                
                config.setJdbcUrl(dbUrl);
                config.setUsername(username);
                config.setPassword(password);
            } catch (URISyntaxException e) {
                throw new RuntimeException("Failed to parse DATABASE_URL", e);
            }
        } else {
            // Use properties from application.properties for development
            config.setJdbcUrl(properties.getUrl());
            config.setUsername(properties.getUsername());
            config.setPassword(properties.getPassword());
        }

        config.setDriverClassName("org.postgresql.Driver");
        
        // Connection pool settings
        if (isProduction) {
            // Production pool settings
            config.setMinimumIdle(1);
            config.setMaximumPoolSize(3);
        } else {
            // Development pool settings
            config.setMinimumIdle(1);
            config.setMaximumPoolSize(5);
        }
        
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(20000);
        config.setMaxLifetime(1200000);
        
        // PostgreSQL specific settings
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        
        return new HikariDataSource(config);
    }
} 