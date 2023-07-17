package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.response.LogResponse;
import com.example.labmedical.repository.model.Log;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogMapper {
    LogResponse map(Log log);
}
