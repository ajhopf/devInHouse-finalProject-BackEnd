package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.DietType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietUpdateRequest {
    @NotBlank(message = "Campo 'dietName' não pode estar em branco")
    @Size(min = 5, max = 100, message = "'dietName' deve ter entre 5 e 100 caracteres")
    private String dietName;
    @NotNull(message = "É necessário inserir uma data para a dieta.")
    private LocalDate dietDate;
    @NotNull(message = "É necessário inserir um horário.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DietType dietType;
    private Boolean status;
    private String description;
}
