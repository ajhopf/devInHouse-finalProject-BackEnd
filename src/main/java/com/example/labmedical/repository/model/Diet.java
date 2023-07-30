package com.example.labmedical.repository.model;

import com.example.labmedical.enums.DietType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name= "diets")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String dietName;
    @Column(nullable = false)
    private LocalDate dietDate;
    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DietType dietType;
    private String description;
    @Column(nullable = false)
    private Boolean status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pacient pacient;
}
