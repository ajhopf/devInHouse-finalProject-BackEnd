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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "exam_date", nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @Column(name = "exam_type",nullable = false)
    private String type;
    @Column(nullable = false)
    private String laboratory;
    @Column(name = "document_url")
    private String documentUrl;
    @Column(nullable = false)
    private String result;
    @Column(nullable = false)
    private Boolean status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;

}
