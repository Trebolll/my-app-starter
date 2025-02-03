package com.example.config.service;

import com.example.config.dto.ResponseLogDto;
import com.example.config.entity.ResponseLog;
import com.example.config.mapper.ResponseLogMapper;
import com.example.config.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final ResponseLogMapper responseLogMapper;
    public ResponseLog save(ResponseLogDto responseLogDto) {
        return responseRepository.save(responseLogMapper.toResponseLog(responseLogDto));
    }
}
