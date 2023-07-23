package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.request.ExamUpdate;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.controller.mapper.ExamMapper;
import com.example.labmedical.repository.ExamRepository;
import com.example.labmedical.repository.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ExamServiceTest {
    @Mock
    private ExamRepository examRepository;
    @Mock
    private ExamMapper examMapper;
    @Mock
    private PacientService pacientService;
    @Mock
    private LogService logService;
    @InjectMocks
    private ExamService examService;

    @Nested
    @DisplayName("Test registerExam Method")
    class registerExamMethod{
        @Test
        @DisplayName("When registering exam and pacientId is not found, it should throw EntityNotFoundException")
        void Test1() {
            ExamRequest examRequest = ExamRequest.builder()
                    .pacientId(1L)
                    .build();
            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);
            assertThrows(EntityNotFoundException.class, () -> examService.registerExam(examRequest));
        }

        @Test
        @DisplayName("When registering exam and pacientId is found, it should save exam")
        void Test2(){
            ExamRequest request = ExamRequest.builder().pacientId(1L).build();
            Pacient pacient = Pacient.builder().id(1L).build();
            Exam exam = Exam.builder().id(1L).pacient(pacient).build();
            ExamResponse examResponse = ExamResponse.builder().build();

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(pacient);
            Mockito.when(examMapper.map(Mockito.any(ExamRequest.class)))
                    .thenReturn(exam);
            Mockito.when(examMapper.map(Mockito.any(Exam.class)))
                    .thenReturn(examResponse);

            examService.registerExam(request);

            Mockito.verify(examRepository, times(1))
                    .save(exam);
        }

    }
    @Nested
    @DisplayName("Test updateExame Method")
    class examExamMethod {

        @Test
        @DisplayName("When updating exam and examId is not found, it should throw EntityNotFoundException")
        void Test1() {
            assertThrows(EntityNotFoundException.class, () -> examService.updateExam(1l, ExamUpdate.builder().build()));
        }
        @Test
        @DisplayName("When exam is found, it should return exam object with the correct id and infos")
        void test2() {
            Exam exam = Exam
                    .builder()
                    .id(1l)
                    .name("Urina")
                    .result("Negativo")
                    .build();

            ExamResponse response = ExamResponse
                    .builder()
                    .id(1l)
                    .name("Urina")
                    .result("Negativo")
                    .build();

            Log log = Log.builder().build();

            Mockito.when(examRepository.existsById(Mockito.anyLong())).thenReturn(true);
            Mockito.when(examRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(exam));
            Mockito.when(examMapper.map(Mockito.any(ExamUpdate.class)))
                    .thenReturn(exam);
            Mockito.when(examRepository.save(Mockito.any(Exam.class)))
                    .thenReturn(exam);
            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(log);
            Mockito.when((examMapper.map(Mockito.any(Exam.class)))).thenReturn(response);

            ExamResponse result = examService.updateExam(1L, ExamUpdate.builder().build());

            assertEquals(1L, result.getId());
            assertEquals("Urina", result.getName());
            assertEquals("Negativo", result.getResult());
        }
    }
    @Nested
    @DisplayName("Tests of getExams method")
    class getExamsTests{
        @Test
        @DisplayName("When no pacientId is present, it should return all exams")
        void test1() {
            List<Exam> exams = List.of(
                    Exam.builder().build(),
                    Exam.builder().build()
            );

            List<ExamResponse> examResponseList = List.of(
                    ExamResponse.builder().build(),
                    ExamResponse.builder().build()
            );

            Mockito.when(examRepository.findAll())
                    .thenReturn(exams);
            Mockito.when(examMapper.map(exams))
                    .thenReturn(examResponseList);

            List<ExamResponse> result = examService.getExams(null);

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When pacientId is present, it should return only that pacients exam")
        void test2() {
            List<Exam> exams = List.of(
                    Exam.builder().build(),
                    Exam.builder().build()
            );

            List<ExamResponse> examResponseList = List.of(
                    ExamResponse.builder().build(),
                    ExamResponse.builder().build()
            );

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(Pacient.builder().build());
            Mockito.when(examRepository.getExamsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(exams);
            Mockito.when(examMapper.map(exams))
                    .thenReturn(examResponseList);

            List<ExamResponse> result = examService.getExams(1L);

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When pacientId is present and there is no pacient with given Id, it should throw EntityNotFoundException")
        void test3() {
            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> examService.getExams(1L));
        }
    }
}