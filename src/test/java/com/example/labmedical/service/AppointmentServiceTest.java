package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.mapper.AppointmentMapper;
import com.example.labmedical.repository.AppointmentRepository;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Pacient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@SpringBootTest
class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private PacientService pacientService;
    @Mock
    private LogService logService;
    @InjectMocks
    private AppointmentService appointmentService;

    @Nested
    @DisplayName("Tests of registerAppointment method")
    class registerAppointmentTests {
        @Test
        @DisplayName("When registering appointment and pacientId is not found, it should throw EntityNotFoundException")
        void test1() {
            AppointmentRegisterRequest request = AppointmentRegisterRequest.builder().pacientId(1L).build();

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                            .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> appointmentService.registerAppointment(request));
        }

        @Test
        @DisplayName("When registering appointment and pacientId is found, it should save appointment")
        void test2() {
            AppointmentRegisterRequest request = AppointmentRegisterRequest.builder().pacientId(1L).build();
            Pacient pacient = Pacient.builder().id(1L).build();
            Appointment appointment = Appointment.builder().id(1L).pacient(pacient).build();
            AppointmentResponse appointmentResponse = AppointmentResponse.builder().build();

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(pacient);
            Mockito.when(appointmentMapper.map(Mockito.any(AppointmentRegisterRequest.class)))
                    .thenReturn(appointment);
            Mockito.when(appointmentMapper.map(Mockito.any(Appointment.class)))
                    .thenReturn(appointmentResponse);

            appointmentService.registerAppointment(request);

            Mockito.verify(appointmentRepository, times(1))
                    .save(appointment);
        }
    }

}