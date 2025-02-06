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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "request_log_id")
    private RequestLog requestLog;
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

}