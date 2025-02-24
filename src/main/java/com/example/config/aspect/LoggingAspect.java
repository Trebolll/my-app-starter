package com.example.config.aspect;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Optional;

import static com.example.config.util.AspectConstant.logs;

@Aspect
@Slf4j
public class LoggingAspect {
    @Before(logs)
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = logs, returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Executed: {}, Return: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = logs, throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        Optional.of(ex)
                .filter(RuntimeException.class::isInstance)
                .map(RuntimeException.class::cast)
                .ifPresent(e -> log.error("Exception in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage()));
    }

    private void init() {
        log.info("initLogAspect");
    }

    private void destroy() {
        log.info("destroyLogAspect");
    }
}
