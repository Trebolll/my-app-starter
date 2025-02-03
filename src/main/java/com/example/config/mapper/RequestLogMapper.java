package com.example.config.mapper;

import com.example.config.dto.RequestLogDto;
import com.example.config.entity.RequestLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestLogMapper {
    RequestLog toRequestLog(RequestLogDto requestLogDto);
    RequestLogDto toRequestLogDto(RequestLog requestLog);
}
