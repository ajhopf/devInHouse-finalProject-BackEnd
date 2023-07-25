package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.ExerciseRequest;
import com.example.labmedical.controller.dtos.response.ExerciseResponse;
import com.example.labmedical.repository.model.User;
import com.example.labmedical.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<ExerciseResponse> createExercise(
            @Valid @RequestBody ExerciseRequest exercise
    ) {
        SecurityContext contexto = SecurityContextHolder.getContext();
        User user = (User) contexto.getAuthentication().getPrincipal();
        var exerciseId = exerciseService.registerExercise(exercise, user).getId();
        String locationUri = "/exercises/" + exerciseId;
        return ResponseEntity.created(URI.create(locationUri)).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<List<ExerciseResponse>> getAllExercises() {
        return ResponseEntity.ok().body(exerciseService.getAll());
    }

    @GetMapping("/{patientName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<List<ExerciseResponse>> getExercisesByPatientName(
            @PathVariable String patientName
    ) {
        return ResponseEntity.ok().body(exerciseService.getExercisesByPatientName(patientName));
    }

    @GetMapping("patientId/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<List<ExerciseResponse>> getExercisesByPatientId(
            @PathVariable Long patientId
    ) {
        return ResponseEntity.ok().body(exerciseService.getExercisesByPatientId(patientId));
    }
}
