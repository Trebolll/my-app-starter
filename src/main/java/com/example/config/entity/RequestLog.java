package com.example.config.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "request_log")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "value")
    private String value;
    @Column(name = "request_method")
    private String requestMethod;
    @Column(name = "http_method")
    private String httpMethod;
    @Column(name = "request_url")
    private String requestUrl;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}