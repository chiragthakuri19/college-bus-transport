package com.college.bus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;

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
            
            // Set proper connector properties using appropriate methods
            connector.setProperty("relaxedQueryChars", "[]{}|");
            
            // Configure using the protocol
            if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol<?> protocol = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();
                protocol.setMaxHttpHeaderSize(65536);
                protocol.setMaxThreads(50);
                protocol.setMinSpareThreads(5);
                protocol.setMaxConnections(100);
                protocol.setAcceptCount(10);
                protocol.setConnectionTimeout(connectionTimeout);
                protocol.setKeepAliveTimeout(120000);
                protocol.setCompression("on");
                protocol.setCompressionMinSize(2048);
                protocol.setNoCompressionUserAgents("gozilla, traviata");
                String compressibleMimeTypes = "text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json";
                protocol.setProperty("compressibleMimeType", compressibleMimeTypes);
            }
            
            connector.setURIEncoding("UTF-8");
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
                    .allowCredentials(!"*".equals(allowedOrigins)) // Only allow credentials when origins are explicitly specified
                    .maxAge(3600);
            }
        };
    }
} 