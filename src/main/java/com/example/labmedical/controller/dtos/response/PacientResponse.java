package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.repository.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacientResponse {
    private Long id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String cpf;
    private String rg;
    private String civilStatus;
    private String phone;
    private String email;
    private String nationality;
    private String emergencyContact;
    private List<String> alergies;
    private List<String> specialCare;
    private String healthInsurance;
    private String healthInsuranceNumber;
    private LocalDate healthInsuranceExpirationDate;
    private Address address;
    private String role;
    private Boolean isActive;
}
