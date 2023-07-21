package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.repository.model.Pacient;
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
    private LocalDate date;
    private LocalTime time;
    private String problemDescription;
    //todo
    //private Medication medication;
    private String dosageAndPrecautions;
    private Boolean isActive;
    private Pacient pacient;
}
