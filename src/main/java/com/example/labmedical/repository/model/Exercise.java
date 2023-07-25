package com.example.labmedical.repository.model;

import com.example.labmedical.enums.ExerciseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "O campo 'Tipo' é obrigatório.")
    private ExerciseType exerciseType;
    @NotNull(message = "O campo 'Quantidade por Semana' é obrigatório.")
    private Double timesPerWeek;
    @NotNull(message = "O campo 'Data' é obrigatório.")
    private LocalDate dateCreated;
    @NotNull(message = "O campo 'Horário' é obrigatório.")
    private LocalTime timeCreated;
    @NotBlank(message = "O campo 'Nome do Série de Exercícios' é obrigatório.")
    @Size(min = 5, max = 100, message = "O nome da série deve ter entre 5 e 100 caracteres.")
    private String description;
    @NotBlank(message = "O campo 'Descrição' é obrigatório.")
    @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres.")
    private String exerciseSeriesName;
    private boolean status = true;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdByUser;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @NotNull(message = "Tem que informar o paciente")
    private Pacient patient;
}
