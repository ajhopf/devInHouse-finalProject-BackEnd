package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.enums.MeasurementUnit;
import com.example.labmedical.enums.MedicineType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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
public class MedicineResponse {

    private Long id;
    private String medicineName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate medicineDate;
    private LocalTime time;
    private MedicineType type;
    private Integer quantity;
    private MeasurementUnit measurementUnit;
    private String comments;
    private boolean status;
    private Long pacientId;


}
