package com.example.labmedical.controller.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRegisterRequest {
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Cep deve estar no formato '12345-123'.")
    private String cep;
    @NotBlank
    private String city;
    @NotBlank
    @Length(min = 2, max = 2, message = "Insira a sigla do estado.")
    private String state;
    @NotBlank
    private String street;
    @NotBlank
    private String houseNumber;
    private String complement;
    @NotBlank
    private String district;
    private String referencePoint;
}
