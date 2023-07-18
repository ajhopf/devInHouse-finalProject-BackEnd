package com.example.labmedical.controller.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetUserPasswordRequest {
    @NotNull
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
