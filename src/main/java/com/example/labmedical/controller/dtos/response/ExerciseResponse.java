package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.enums.ExerciseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
public class ExerciseResponse {
    private Long id;
    private String description;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;
    private Double timesPerWeek;
    private String exerciseSeriesName;
    private Long patientId;
    private boolean status = true;
}
