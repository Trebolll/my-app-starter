package com.example.config.aspect;

import com.example.config.dto.RequestLogDto;
import com.example.config.dto.ResponseLogDto;
import com.example.config.entity.RequestLog;
import com.example.config.service.RequestService;
import com.example.config.service.ResponseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.example.config.util.AspectConstant.audit;
import static com.example.config.util.AspectConstant.requestLogAttribute;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {
    private final RequestService requestLogService;
    private final ResponseService responseLogService;
    private final ObjectMapper objectMapper;

    @Before(audit)
    public void auditExecutionRequest(JoinPoint joinPoint) {
        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .ifPresent(request -> {
                    RequestLog requestLog = requestLogService.save(RequestLogDto.builder()
                            .id(null)
                            .value(joinPoint.getArgs()[0].toString())
                            .requestMethod(joinPoint.getSignature().getName())
                            .httpMethod(request.getMethod())
                            .requestUrl(request.getRequestURI())
                            .timestamp(LocalDateTime.now())
                            .build());
                    request.setAttribute(requestLogAttribute, requestLog.getId());
                });
    }

    @SneakyThrows
    @AfterReturning(pointcut = audit, returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        Integer statusCode = (result instanceof ResponseEntity<?> responseEntity) ? responseEntity.getStatusCode().value() : 0;
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ServletRequest request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            responseLogService.save(ResponseLogDto.builder()
                    .value(joinPoint.getArgs()[0].toString())
                    .status(statusCode)
                    .requestLog(requestLogService.findById((UUID) request.getAttribute(requestLogAttribute)).orElse(null))
                    .timestamp(LocalDateTime.now())
                    .responseBody(objectMapper.writeValueAsString(Objects.requireNonNull(result)))
                    .build());
        }
    }

    @SneakyThrows
    @AfterThrowing(pointcut = audit, throwing = "ex")
    public void logAfterMethodThrowing(JoinPoint joinPoint, Throwable ex) {
        Integer statusCode = Optional.ofNullable(ex)
                .map(e -> (e instanceof ResponseStatusException rse) ? rse.getStatusCode().value() : HttpStatus.BAD_REQUEST.value())
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ServletRequest request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        responseLogService.save(ResponseLogDto.builder()
                .value(joinPoint.getArgs()[0].toString())
                .status(statusCode)
                .timestamp(LocalDateTime.now())
                .requestLog(requestLogService.findById((UUID) request.getAttribute(requestLogAttribute)).orElse(null))
                .responseBody(objectMapper.writeValueAsString(Objects.requireNonNull(ex).getMessage()))
                .build());
    }

    private void init() {
        log.info("initAuditAspect");
    }

    private void destroy() {
        log.info("destroyAuditAspect");
    }
}


