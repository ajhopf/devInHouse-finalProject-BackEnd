package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @Size(min = 8, max = 64)
    private String name;
    @NotBlank
    private String gender;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    private String telephone;
    @NotBlank
    @Email
    private String email;
    @Size(min = 6)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
