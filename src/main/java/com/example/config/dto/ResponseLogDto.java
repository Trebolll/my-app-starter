package com.example.config.dto;

import com.example.config.entity.RequestLog;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ResponseLogDto(UUID id,
                             String value,
                             String responseBody,
                             LocalDateTime timestamp,
                             Integer status,
                             RequestLog requestLog) {
}
