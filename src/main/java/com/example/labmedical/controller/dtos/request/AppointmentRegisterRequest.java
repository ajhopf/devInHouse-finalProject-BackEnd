package com.example.labmedical.controller.dtos.request;

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
public class AppointmentRegisterRequest {
    @NotBlank(message = "Campo 'appointmentReason' não pode estar em branco")
    @Size(min = 8, max = 64, message = "'AppointmentReason' deve ter entre 8 e 64 caracteres")
    private String appointmentReason;
    @NotNull(message = "É necessário inserir uma data para a consulta.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;
    @NotNull(message = "É necessário inserir um horário.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    @NotBlank
    @Size(min = 16, max = 1024, message = "'problemDescription' deve ter entre 16 e 1024 caracteres")
    private String problemDescription;
    @NotBlank
    @Size(min = 16, max = 256, message = "'dosageAndPrecautions' deve ter entre 16 e 256 caracteres")
    private String dosageAndPrecautions;
    @NotNull
    private Boolean isActive;
    @NotNull
    private Long pacientId;
    @NotNull
    private Long medicineId;
}
