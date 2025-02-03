package com.example.config.mapper;

import com.example.config.dto.ResponseLogDto;
import com.example.config.entity.ResponseLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseLogMapper {
    ResponseLog toResponseLog(ResponseLogDto responseLogDto);
    ResponseLogDto toResponseLogDto(ResponseLog responseLog);
}
