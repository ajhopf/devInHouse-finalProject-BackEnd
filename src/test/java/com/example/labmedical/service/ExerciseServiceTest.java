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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Nested
    @DisplayName("Tests of getExercisesByPatientName Method")
    class getExercisesByPatientNameMethodTests {

        @Test
        @DisplayName("When receives patientName, it should return a list of exercises by patientName")
        void test1() {

            User user = User.builder().name("teste").build();
            Pacient patient = Pacient.builder().id(1L).name("teste").build();
            Exercise mockExercise = Exercise.builder().id(1L).patient(patient).build();
            ExerciseResponse exerciseResponse = ExerciseResponse.builder().build();
            when(exerciseMapper.map(mockExercise)).thenReturn(exerciseResponse);
            when(exerciseRepository.findByPatientNameContainingIgnoreCase(anyString())).thenReturn(List.of(mockExercise));
            exerciseService.getExercisesByPatientName("teste");
            verify(exerciseRepository).findByPatientNameContainingIgnoreCase(anyString());
            verify(exerciseMapper).map(mockExercise);
            verify(log).success(anyString());
        }

    }

    @Nested
    @DisplayName("Tests of getAll Method")
    class getAllMethodTests {

        @Test
        @DisplayName("When asked for all exercises, it should return a list of exercises")
        void test1() {
            Pacient patient = Pacient.builder().id(1L).name("teste").build();
            Exercise mockExercise = Exercise.builder().id(1L).patient(patient).build();
            ExerciseResponse exerciseResponse = ExerciseResponse.builder().build();
            when(exerciseMapper.map(mockExercise)).thenReturn(exerciseResponse);
            when(exerciseRepository.findAll()).thenReturn(List.of(mockExercise));
            exerciseService.getAll();
            verify(exerciseRepository).findAll();
            verify(exerciseMapper).map(mockExercise);
            verify(log).success(anyString());
        }

    }

    @Nested
    @DisplayName("Tests of getExercisesByPatientId Method")
    class getExercisesByPatientIdTests {

        @Test
        @DisplayName("when receives patientId, it should return a list of exercises by patientId")
        void test1() {
            Pacient patient = Pacient.builder().id(1L).name("teste").build();
            Exercise mockExercise = Exercise.builder().id(1L).patient(patient).build();
            ExerciseResponse exerciseResponse = ExerciseResponse.builder().build();
            when(exerciseMapper.map(mockExercise)).thenReturn(exerciseResponse);
            when(exerciseRepository.findByPatientId(1L)).thenReturn(List.of(mockExercise));
            exerciseService.getExercisesByPatientId(1L);
            verify(exerciseRepository).findByPatientId(1L);
            verify(exerciseMapper).map(mockExercise);
            verify(log).success(anyString());
        }

    }

    @Nested
    @DisplayName("Tests of deleteExercise Method")
    class deleteExerciseTests {

        @Test
        @DisplayName("When receives exerciseId, it should delete the exercise")
        void test1() {
            doNothing().when(exerciseRepository).deleteById(1L);
            exerciseService.deleteExercise(1L);
            verify(exerciseRepository).deleteById(1L);
            verify(log).success(anyString());
        }

    }
}