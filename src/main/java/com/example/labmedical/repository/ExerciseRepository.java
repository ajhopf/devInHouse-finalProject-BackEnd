package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByPatientNameContainingIgnoreCase(String patientName);

    List<Exercise> findByPatientId(Long patientId);
}

