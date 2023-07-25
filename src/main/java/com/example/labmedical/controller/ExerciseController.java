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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/exercicios")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<ExerciseResponse> createExercise(
            @Valid @RequestBody ExerciseRequest exercise
    ){
        SecurityContext contexto = SecurityContextHolder.getContext();
        User user = (User) contexto.getAuthentication().getPrincipal();
        var exerciseId = exerciseService.registerExercise(exercise, user).getId();
        String locationUri = "/exercises/" + exerciseId;
        return ResponseEntity.created(URI.create(locationUri)).build();
    }
}
