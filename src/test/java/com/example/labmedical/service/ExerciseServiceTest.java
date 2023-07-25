package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ExerciseRequest;
import com.example.labmedical.controller.dtos.response.ExerciseResponse;
import com.example.labmedical.controller.mapper.ExerciseMapper;
import com.example.labmedical.enums.ExerciseType;
import com.example.labmedical.repository.ExerciseRepository;
import com.example.labmedical.repository.model.Exercise;
import com.example.labmedical.repository.model.Pacient;
import com.example.labmedical.repository.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExerciseServiceTest {
    @InjectMocks
    private ExerciseService exerciseService;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseMapper exerciseMapper;
    @Mock
    private LogService log;
    @Nested
    @DisplayName("Tests of registerExercise Method")
    class registerExerciseMethodTests {
        @Test
        @DisplayName("When receives ExerciseRequest, it should save exercise and return ExerciseResponse")
        void test1() {
            ExerciseRequest exerciseRequest = ExerciseRequest.builder().build();
            User user = User.builder().name("teste").build();
            Pacient patient = Pacient.builder().name("teste").build();
            Exercise mockExercise = Exercise.builder().patient(patient).build();
            when(exerciseMapper.map(exerciseRequest)).thenReturn(mockExercise);
            when(exerciseRepository.save(mockExercise)).thenReturn(mockExercise);
            exerciseService.registerExercise(exerciseRequest, user);
            verify(exerciseMapper).map(exerciseRequest);
            verify(exerciseRepository).save(mockExercise);
            verify(log).success(anyString());

        }

    }
}