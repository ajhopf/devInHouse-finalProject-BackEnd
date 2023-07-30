package com.example.labmedical.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private String appointmentReason;
    private LocalDate appointmentDate;
    private LocalTime time;
    private String problemDescription;
    private String dosageAndPrecautions;
    private Boolean isActive;
    private Long pacientId;
    private Long medicineId;
}
