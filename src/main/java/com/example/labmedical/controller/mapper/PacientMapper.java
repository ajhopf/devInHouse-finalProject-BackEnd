package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientUpdateRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.enums.Role;
import com.example.labmedical.repository.model.Pacient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PacientMapper {
    PacientResponse map(Pacient source);

    @Mapping(source = "address", target = "address")
    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "isActive", expression = "java(mapIsActive())")
    Pacient map(PacientRegisterRequest source);

    @Mapping(source = "address", target = "address")
    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "isActive", expression = "java(mapIsActive())")
    Pacient map(PacientUpdateRequest source);

    default Role mapRole() {
        return Role.ROLE_PACIENT; // Set the default value for the role property
    }

    default Boolean mapIsActive() {
        return true;
    }
}
