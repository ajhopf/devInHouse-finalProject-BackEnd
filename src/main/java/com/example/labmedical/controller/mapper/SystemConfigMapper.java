package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.controller.dtos.response.SystemConfigResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemConfigMapper {
    SystemConfigResponse map(SystemConfigRequest source);
}
