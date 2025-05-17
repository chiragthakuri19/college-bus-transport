package com.college.bus.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Value("${DATABASE_URL:#{null}}")
    private String databaseUrl;

    @Value("${spring.datasource.url:#{null}}")
    private String fallbackUrl;

    @Value("${spring.datasource.username:#{null}}")
    private String fallbackUsername;

    @Value("${spring.datasource.password:#{null}}")
    private String fallbackPassword;

    @Primary
    @Bean
    public DataSource dataSource() throws URISyntaxException {
        HikariConfig config = new HikariConfig();
        
        if (StringUtils.hasText(databaseUrl)) {
            // Parse Render's DATABASE_URL
            URI dbUri = new URI(databaseUrl);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            
            config.setJdbcUrl(dbUrl);
            config.setUsername(username);
            config.setPassword(password);
        } else {
            // Use fallback configuration
            config.setJdbcUrl(fallbackUrl);
            config.setUsername(fallbackUsername);
            config.setPassword(fallbackPassword);
        }

        config.setDriverClassName("org.postgresql.Driver");
        
        // Connection pool settings
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(3);
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