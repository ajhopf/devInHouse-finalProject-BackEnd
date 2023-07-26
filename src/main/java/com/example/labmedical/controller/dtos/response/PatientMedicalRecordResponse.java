package com.example.labmedical.controller.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class PatientMedicalRecordResponse {
    private String name;
    private String healthInsurance;
    private String healthInsuranceNumber;
    private List<String> alergies;
    private List<String> specialCare;
}
