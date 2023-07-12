package com.example.labmedical.controller.dtos;

import com.example.labmedical.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Role role;
    private String name;
    private String photoUrl;
}
