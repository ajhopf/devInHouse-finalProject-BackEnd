package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.repository.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User map(UserRegisterRequest source);
}
