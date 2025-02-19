package com.example.config.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RequestLogDto(UUID id,
                            String value,
                            String requestMethod,
                            String httpMethod,
                            String requestUrl,
                            LocalDateTime timestamp) {
}
