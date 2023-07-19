package com.example.labmedical.controller.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String cidade;
    @NotBlank
    @Length(min = 2, max = 2, message = "Insira a sigla do estado.")
    private String estado;
    @NotBlank
    private String logradouro;
    @NotNull
    private Integer numero;
    private String complemento;
    @NotBlank
    private String bairro;
    private String pontoReferencia;
}
