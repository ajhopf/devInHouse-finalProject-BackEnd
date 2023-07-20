package com.example.labmedical.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "special_care")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;
    @Column(nullable = false)
    private String specialCare;
}