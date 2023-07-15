package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponse {
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
