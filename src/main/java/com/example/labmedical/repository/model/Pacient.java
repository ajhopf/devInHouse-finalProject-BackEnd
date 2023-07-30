package com.example.labmedical.repository.model;

import com.example.labmedical.enums.CivilStatus;
import com.example.labmedical.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name= "pacients")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pacient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String rg;
    @Column(nullable = false)
    private CivilStatus civilStatus;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private String emergencyContact;
    private String healthInsurance;
    private String healthInsuranceNumber;
    private LocalDate healthInsuranceExpirationDate;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private Boolean isActive;
}
