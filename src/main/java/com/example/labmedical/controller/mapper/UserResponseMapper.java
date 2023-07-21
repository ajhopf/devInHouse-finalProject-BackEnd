package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.response.UserResponse;
import com.example.labmedical.repository.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {
    UserResponse map(User source);
}
