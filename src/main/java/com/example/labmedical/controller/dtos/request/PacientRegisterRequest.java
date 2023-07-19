package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.CivilStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacientRegisterRequest {
    @NotBlank(message = "Campo 'name' não pode estar em branco")
    @Size(min = 8, max = 64, message = "Nome deve ter entre 8 e 64 caracteres")
    private String name;
    @NotBlank
    private String gender;
    @NotNull(message = "É necessário inserir uma data de nascimento.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    @Pattern(regexp = "([0-9]{3}[.][0-9]{3}[.][0-9]{3}-[0-9]{2})", message = "CPF deve estar no formato 000.000.000-00")
    @NotBlank
    private String cpf;
    private String rg;
    @NotNull(message = "É necessário inserir um estado civil.")
    private CivilStatus civilStatus;
    @NotBlank
    @Pattern(regexp = "(^\\(\\d{2}\\) \\d \\d{4}-\\d{5}$)", message = "Telefone deve estar no formato (99) 9 9999-99999")
    private String phone;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8, max = 64, message = "Naturalidade deve ter entre 8 e 64 caracteres")
    private String nationality;
    @NotBlank
    @Pattern(regexp = "(^\\(\\d{2}\\) \\d \\d{4}-\\d{5}$)", message = "Telefone de emergência deve estar no formato (99) 9 9999-99999")
    private String emergencyContact;
    private List<String> alergies;
    private List<String> specialCare;
    private String healthInsurance;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate healthInsuranceExpirationDate;
    @NotNull
    private AddressRegisterRequest address;
}
