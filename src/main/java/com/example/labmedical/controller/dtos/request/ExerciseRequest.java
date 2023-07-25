package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.ExerciseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ExerciseRequest {
    @NotBlank(message = "O campo 'Nome do Série de Exercícios' é obrigatório.")
    @Size(min = 5, max = 100, message = "O nome da série deve ter entre 5 e 100 caracteres.")
    private String description;

    @NotNull(message = "O campo 'Data' é obrigatório.")
    private LocalDate dateCreated;

    @NotNull(message = "O campo 'Horário' é obrigatório.")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "O campo 'Hora' deve estar no formato 'HH:mm:ss'.")
    private String timeCreated;

    @NotNull(message = "O campo 'Tipo' é obrigatório.")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @NotNull(message = "O campo 'Quantidade por Semana' é obrigatório.")
    @DecimalMax(value = "99.99", inclusive = false, message = "O campo 'Quantidade por Semana' deve ter no máximo duas casas decimais.")
    private Double timesPerWeek;

    @NotBlank(message = "O campo 'Descrição' é obrigatório.")
    @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres.")
    private String exerciseSeriesName;

    @NotNull(message = "Tem que informar o paciente")
    private Long patientId;

    private boolean status = true;
}
