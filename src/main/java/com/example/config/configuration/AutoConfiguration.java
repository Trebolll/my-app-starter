package com.example.config.configuration;

import com.example.config.aspect.AuditAspect;
import com.example.config.aspect.LoggingAspect;
import com.example.config.service.RequestService;
import com.example.config.service.ResponseService;
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

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public AuditAspect auditAspect(RequestService requestService, ResponseService responseService) {
        return new AuditAspect(requestService, responseService);
    }

}

