package com.example.labmedical.repository.model;

import com.example.labmedical.enums.ExerciseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "exercises")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;
    private Integer timesPerWeek;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private String description;
    private String exerciseSeriesName;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdByUser;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Pacient patient;
}
