package com.example.config.aspect;

import com.example.config.dto.RequestLogDto;
import com.example.config.dto.ResponseLogDto;
import com.example.config.service.RequestService;
import com.example.config.service.ResponseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import java.util.UUID;

import static com.example.config.util.AspectConstant.audit;
import static com.example.config.util.AspectConstant.requestLogAttribute;

@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private final RequestService requestLogService;
    private final ResponseService responseLogService;
    private final ObjectMapper objectMapper;

    @Before(audit)
    public void auditExecutionRequest(JoinPoint joinPoint) {
        var request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var requestLogDto =  RequestLogDto.builder()
                .id(null)
                .value(request.getParameter(request.getParameterNames().nextElement()))
                .requestMethod(joinPoint.getSignature().getName())
                .httpMethod(request.getMethod())
                .requestUrl(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        var requestLog = requestLogService.save(requestLogDto);
        request.setAttribute(requestLogAttribute, requestLog.getId());
    }

    @SneakyThrows
    @AfterReturning(pointcut = audit, returning = "result")
    public void logAfterMethod(Object result) {
        int statusCode;
        if (result instanceof ResponseEntity<?> responseEntity) {
            statusCode = responseEntity.getStatusCode().value();
            var request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            var requestLogId = (UUID) request.getAttribute(requestLogAttribute);
            var requestLog = requestLogService.findById(requestLogId).orElse(null);
            var responseLogDto = ResponseLogDto.builder()
                    .value(request.getParameter(request.getParameterNames().nextElement()))
                    .status(statusCode)
                    .requestLog(requestLog)
                    .timestamp(LocalDateTime.now())
                    .responseBody(objectMapper.writeValueAsString(result))
                    .build();
            responseLogService.save(responseLogDto);
        }
    }

    @SneakyThrows
    @AfterThrowing(pointcut = audit, throwing = "ex")
    public void logAfterMethodThrowing(Throwable ex) {
        var statusCode = (ex instanceof ResponseStatusException responseStatusException)
                ? responseStatusException.getStatusCode().value()
                : (ex instanceof NullPointerException)
                ? HttpStatus.BAD_REQUEST.value()
                : (ex != null)
                ? HttpStatus.BAD_REQUEST.value()
                : HttpStatus.INTERNAL_SERVER_ERROR.value();
        var request = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var requestLogId = (UUID) request.getAttribute(requestLogAttribute);
        var requestLog = requestLogService.findById(requestLogId).orElse(null);
        var responseLog = ResponseLogDto.builder()
                .value(request.getParameter(request.getParameterNames().nextElement()))
                .status(statusCode)
                .timestamp(LocalDateTime.now())
                .requestLog(requestLog)
                .responseBody(objectMapper.writeValueAsString(Objects.requireNonNull(ex).getStackTrace()))
                .build();
        responseLogService.save(responseLog);
    }
}


