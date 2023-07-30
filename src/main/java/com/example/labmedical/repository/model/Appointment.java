package com.example.labmedical.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "appointments")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String appointmentReason;
    @Column(nullable = false)
    private LocalDate appointmentDate;
    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    private String problemDescription;
    @Column(nullable = false)
    private String dosageAndPrecautions;
    @Column(nullable = false)
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Medicine medicine;
}
