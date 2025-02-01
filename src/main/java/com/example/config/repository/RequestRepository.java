package com.example.config.repository;

import com.example.config.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<RequestLog, UUID> {
}