package com.example.config.service;

import com.example.config.dto.RequestLogDto;
import com.example.config.entity.RequestLog;
import com.example.config.mapper.RequestLogMapper;
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
    private final RequestLogMapper requestLogMapper;
    public RequestLog save(RequestLogDto requestLogDto) {
        return requestRepository.save(requestLogMapper.toRequestLog(requestLogDto));
    }
    @Transactional(readOnly = true)
    public Optional<RequestLog> findById(UUID id) { return  requestRepository.findById(id);}
}
