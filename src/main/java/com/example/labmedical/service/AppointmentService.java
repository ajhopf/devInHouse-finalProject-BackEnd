package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.mapper.AppointmentMapper;
import com.example.labmedical.repository.AppointmentRepository;
import com.example.labmedical.repository.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private LogService logService;

    public AppointmentResponse registerAppointment(AppointmentRegisterRequest request) {
        pacientService.getPacientById(request.getPacientId());

        Appointment appointment = appointmentMapper.map(request);
        appointmentRepository.save(appointment);

        logService.success("Consulta registrada. Id consulta: " + appointment.getId() + "; Id Paciente: " + appointment.getPacient().getId());

        return appointmentMapper.map(appointment);
    }

}
