package com.example.labmedical.controller.dtos.response;

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
public class UserResponse {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String photoUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String gender;
    private String cpf;
    private String telephone;
}