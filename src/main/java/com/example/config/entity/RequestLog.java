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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request_log")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "value")
    private String value;
    @Column(name = "requestMethod")
    private String requestMethod;
    @Column(name = "httpMethod")
    private String httpMethod;
    @Column(name = "requestUrl")
    private String requestUrl;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLog that = (RequestLog) o;
        return Objects.equals(id, that.id)
                && Objects.equals(value, that.value)
                && Objects.equals(requestMethod, that.requestMethod)
                && Objects.equals(httpMethod, that.httpMethod)
                && Objects.equals(requestUrl, that.requestUrl)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, requestMethod, httpMethod, requestUrl, timestamp);
    }
}