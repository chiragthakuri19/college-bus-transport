package com.college.bus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.core.env.Environment;

@Configuration
public class WebServerConfig {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${server.tomcat.connection-timeout:20000}")
    private int connectionTimeout;

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers((connector) -> {
            connector.setPort(serverPort);
            connector.setProperty("relaxedQueryChars", "[]{}|");
            connector.setProperty("maxHttpHeaderSize", "65536");
            connector.setProperty("maxThreads", "50");
            connector.setProperty("minSpareThreads", "5");
            connector.setProperty("maxConnections", "100");
            connector.setProperty("acceptCount", "10");
            connector.setProperty("connectionTimeout", String.valueOf(connectionTimeout));
            connector.setProperty("maxKeepAliveRequests", "100");
            connector.setProperty("keepAliveTimeout", "120000");
            connector.setProperty("compression", "on");
            connector.setProperty("compressionMinSize", "2048");
            connector.setProperty("noCompressionUserAgents", "gozilla, traviata");
            connector.setProperty("compressableMimeType", 
                "text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json");
            connector.setProperty("URIEncoding", "UTF-8");
        });
        
        return tomcat;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins(allowedOrigins.split(","))
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")
                    .exposedHeaders("Authorization")
                    .allowCredentials("*".equals(allowedOrigins) ? false : true)
                    .maxAge(3600);
            }
        };
    }
} 