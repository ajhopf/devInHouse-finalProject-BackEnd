package com.example.labmedical.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "exams")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime hour;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String laboratory;
    private String documentUrl;
    @Column(nullable = false)
    private String result;
    @Column(nullable = false)
    private Boolean status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;

}
