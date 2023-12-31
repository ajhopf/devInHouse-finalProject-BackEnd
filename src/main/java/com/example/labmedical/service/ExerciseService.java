package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ExerciseRequest;
import com.example.labmedical.controller.dtos.response.ExerciseResponse;
import com.example.labmedical.controller.mapper.ExerciseMapper;
import com.example.labmedical.repository.ExerciseRepository;
import com.example.labmedical.repository.model.Exercise;
import com.example.labmedical.repository.model.User;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        ex.setStatus(true);
        exerciseRepository.save(ex);
        logger.success("O usuario " + user.getName() + " gerou um exercicio para o paciente " + ex.getPatient().getName());
        return exerciseMapper.map(ex);
    }

    public List<ExerciseResponse> getExercisesByPatientName(String patientName) {
        List<Exercise> listaExercicios = exerciseRepository.findByPatientNameContainingIgnoreCase(patientName);
        logger.success("Foram buscadas os exercicios do paciente " + patientName);
        return listaExercicios.parallelStream().map(exercise -> exerciseMapper.map(exercise)).toList();
    }

    public List<ExerciseResponse> getAll() {
        List<Exercise> listaExercicios = exerciseRepository.findAll();
        logger.success("Foram buscadas todos os exercicios");
        return listaExercicios.parallelStream().map(exercise -> exerciseMapper.map(exercise)).toList();
    }

    public List<ExerciseResponse> getExercisesByPatientId(Long patientId) {
        List<Exercise> listaExercicios = exerciseRepository.findByPatientId(patientId);
        logger.success("Foram buscadas os exercicios do paciente " + patientId);
        return listaExercicios.parallelStream().map(exercise -> exerciseMapper.map(exercise)).toList();
    }

    public void deleteExercise(Long exerciseId) {
        if(!exerciseRepository.existsById(exerciseId))
            throw new EntityNotFoundException("Esse id nao existe");
        exerciseRepository.deleteById(exerciseId);
        logger.success("Foi deletado o exercicio " + exerciseId);
    }

    public void updateExercise(Long exerciseId, ExerciseRequest exercise) {
        if(!exerciseRepository.existsById(exerciseId))
            throw new EntityNotFoundException("Esse id nao existe");
        Exercise ex = exerciseMapper.map(exercise);
        ex.setId(exerciseId);
        exerciseRepository.save(ex);
        logger.success("O exercicio " + exerciseId + " foi atualizado");
    }
}
