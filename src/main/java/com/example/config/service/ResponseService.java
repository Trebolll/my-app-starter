package com.example.config.service;
import com.example.config.entity.ResponseLog;
import com.example.config.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;
    public ResponseLog save(ResponseLog responseLog) {
        return responseRepository.save(responseLog);
    }
}
