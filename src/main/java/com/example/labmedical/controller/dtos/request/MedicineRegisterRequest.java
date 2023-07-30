package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.enums.MeasurementUnit;
import com.example.labmedical.enums.MedicineType;
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
public class MedicineRegisterRequest {

    @Column(nullable = false)
    private String medicineName;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate medicineDate;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    @Column(nullable = false)
    private MedicineType type;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private MeasurementUnit measurementUnit;
    @Column(nullable = false)
    private String comments;
    @Column(nullable = false)
    private boolean status;
    @Column(nullable = false)
    private Long pacientId;

}
