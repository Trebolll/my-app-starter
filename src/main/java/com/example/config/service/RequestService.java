package com.example.config.service;

import com.example.config.entity.RequestLog;
import com.example.config.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    public RequestLog save(RequestLog requestLog) {
        return requestRepository.save(requestLog);
    }
    @Transactional(readOnly = true)
    public Optional<RequestLog> findById(UUID id) {   return  requestRepository.findById(id);}
}
