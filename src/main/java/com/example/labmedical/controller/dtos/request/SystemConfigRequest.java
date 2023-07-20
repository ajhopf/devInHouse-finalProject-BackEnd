package com.example.labmedical.controller.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SystemConfigRequest {
    @Size(min = 4, max = 20, message = "O nome da empresa tem que ter entre 4 e 20 caracteres.")
    private String companyName;
    @NotEmpty(message = "A logo não pode ser vazia")
    @URL(message = "A logoUrl deve ser uma URL válida")
    private String logoUrl;
    @NotEmpty(message = "A cor primaria não pode ser vazia")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "A cor da fonte deve estar no formato hexadecimal")
    private String primaryColor;
    @NotEmpty(message = "A cor segundaria não pode ser vazia")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "A cor da fonte deve estar no formato hexadecimal")
    private String secundaryColor;
    @NotEmpty(message = "A cor da fonte não pode ser vazia")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "A cor da fonte deve estar no formato hexadecimal")
    private String fontColor;
}
