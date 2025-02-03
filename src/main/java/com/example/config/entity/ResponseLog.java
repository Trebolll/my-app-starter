package com.example.config.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "response_log")
public class ResponseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "value")
    private String value;
    @Column(name = "responseBody", columnDefinition = "TEXT")
    private String responseBody;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "requestLogId")
    private RequestLog requestLog;
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseLog that = (ResponseLog) o;
        return Objects.equals(id, that.id)
                && Objects.equals(value, that.value)
                && Objects.equals(responseBody, that.responseBody)
                && Objects.equals(timestamp, that.timestamp)
                && Objects.equals(status, that.status)
                && Objects.equals(requestLog, that.requestLog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, responseBody, timestamp, status, requestLog);
    }
}