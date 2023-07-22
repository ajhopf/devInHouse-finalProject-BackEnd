package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.controller.mapper.ExamMapper;
import com.example.labmedical.repository.ExamRepository;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Exam;
import com.example.labmedical.repository.model.Pacient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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

}