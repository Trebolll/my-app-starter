package com.example.config.configuration;

import com.example.config.aspect.AuditAspect;
import com.example.config.aspect.LoggingAspect;
import com.example.config.service.RequestService;
import com.example.config.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.config.repository")
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = "com.example.config.entity")
public class AutoConfiguration {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    @ConditionalOnProperty(name = "com.example.config.log", havingValue = "true")
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @ConditionalOnProperty(name = "com.example.config.audit", havingValue = "true")
    public AuditAspect auditAspect(RequestService requestService, ResponseService responseService, ObjectMapper objectMapper) {
        return new AuditAspect(requestService, responseService, objectMapper);
    }

}

