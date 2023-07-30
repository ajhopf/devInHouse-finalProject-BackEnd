package com.example.labmedical.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "alergies")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;
    @Column(nullable = false)
    private String alergy;
}