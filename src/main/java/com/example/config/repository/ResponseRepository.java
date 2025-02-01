package com.example.config.repository;

import com.example.config.entity.ResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<ResponseLog, UUID> {
}