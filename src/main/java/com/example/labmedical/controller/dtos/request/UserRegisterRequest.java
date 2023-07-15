package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotNull
    @Size(min = 8, max = 64)
    private String name;
    @NotNull
    @NotBlank
    private String gender;
    @NotNull
    @NotBlank
//    habilitar depois
//    @CPF
    private String cpf;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d\\s\\d{4}-\\d{4}$", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String telephone;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Size(min = 6)
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
