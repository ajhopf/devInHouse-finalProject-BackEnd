package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.DietRegisterRequest;
import com.example.labmedical.controller.dtos.response.DietResponse;
import com.example.labmedical.repository.model.Diet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietMapper {
    @Mapping(target = "pacient.id", source = "pacientId")
    Diet map(DietRegisterRequest source);
    @Mapping(target = "pacientId", source = "pacient.id")
    DietResponse map(Diet source);
}
