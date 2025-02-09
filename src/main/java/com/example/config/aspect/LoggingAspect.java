package com.example.config.aspect;

import com.example.config.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class LoggingAspect {
    @Before("@annotation(com.example.config.annotation.Log)")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature().getName());
    }
    @AfterReturning(pointcut = "@annotation(com.example.config.annotation.Log)", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Method executed: {}, Return value: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "@annotation(log)", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Log log, Throwable ex) {
        if (ex instanceof RuntimeException) {
            LoggingAspect.log.error("Exception in method: {} with message: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
        }
    }
}
