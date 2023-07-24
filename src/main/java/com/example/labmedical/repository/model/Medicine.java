package com.example.labmedical.repository.model;

import com.example.labmedical.enums.MeasurementUnit;
import com.example.labmedical.enums.MedicineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name= "medicines")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String medicineName;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    private MedicineType type;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private MeasurementUnit measurementUnit;
    @Column(nullable = false)
    private String comments;
    @Column(nullable = false)
    private boolean status;
    @ManyToOne
    private Pacient pacient;

}
