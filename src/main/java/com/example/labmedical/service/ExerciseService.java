package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ExerciseRequest;
import com.example.labmedical.controller.dtos.response.ExerciseResponse;
import com.example.labmedical.controller.mapper.ExerciseMapper;
import com.example.labmedical.repository.ExerciseRepository;
import com.example.labmedical.repository.model.Exercise;
import com.example.labmedical.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseMapper exerciseMapper;
    @Autowired
    private LogService logger;

    public ExerciseResponse registerExercise(ExerciseRequest exercise, User user) {
        Exercise ex = exerciseMapper.map(exercise);
        ex.setCreatedByUser(user);
        exerciseRepository.save(ex);
        logger.success("O usuario " + user.getName() + " gerou um exercicio para o paciente " + ex.getPatient().getName());
        return exerciseMapper.map(ex);
    }
}
