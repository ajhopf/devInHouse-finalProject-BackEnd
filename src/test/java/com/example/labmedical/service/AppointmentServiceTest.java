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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Tests of getAppointments method")
    class getAppointmentsTests{
        @Test
        @DisplayName("When no pacientId is present, it should return all appointments")
        void test1() {
            List<Appointment> appointments = List.of(
                    Appointment.builder().build(),
                    Appointment.builder().build()
            );

            List<AppointmentResponse> appointmentResponseList = List.of(
                    AppointmentResponse.builder().build(),
                    AppointmentResponse.builder().build()
            );

            Mockito.when(appointmentRepository.findAll())
                    .thenReturn(appointments);
            Mockito.when(appointmentMapper.map(appointments))
                    .thenReturn(appointmentResponseList);

            List<AppointmentResponse> result = appointmentService.getAppointments(null);

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When pacientId is present, it should return only that pacients appointments")
        void test2() {
            List<Appointment> appointments = List.of(
                    Appointment.builder().build(),
                    Appointment.builder().build()
            );

            List<AppointmentResponse> appointmentResponseList = List.of(
                    AppointmentResponse.builder().build(),
                    AppointmentResponse.builder().build()
            );

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(Pacient.builder().build());
            Mockito.when(appointmentRepository.getAppointmentsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(appointments);
            Mockito.when(appointmentMapper.map(appointments))
                    .thenReturn(appointmentResponseList);

            List<AppointmentResponse> result = appointmentService.getAppointments(1L);

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When pacientId is present and there is no pacient with given Id, it should throw EntityNotFoundException")
        void test3() {
            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> appointmentService.getAppointments(1L));
        }
    }

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

    @Nested
    @DisplayName("Tests of deleteAppointment method")
    class deleteAppointmentTests {
        @Test
        @DisplayName("When no appointment is found with given id, it should throw EntityNotFoundException")
        void test1() {
            assertThrows(EntityNotFoundException.class, () -> appointmentService.deleteAppointment(Mockito.anyLong()));
        }

        @Test
        @DisplayName("When appointment is found with given id, it should delete it")
        void test2() {
            Appointment appointment = Appointment.builder().build();

            Mockito.when(appointmentRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(appointment));

            appointmentService.deleteAppointment(1L);

            Mockito.verify(appointmentRepository, times(1))
                    .delete(appointment);
        }
    }

}