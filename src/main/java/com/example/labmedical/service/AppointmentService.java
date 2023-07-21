package com.example.labmedical.service;

import com.example.labmedical.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
//    @Autowired
//    private AppointmentMapper appointmentMapper;
//    @Autowired
//    private PacientService pacientService;
//
//    public AppointmentResponse registerAppointment(AppointmentRegisterRequest request) {
//        Long pacientId = request.getPacientId();
//        Pacient pacient = pacientService.getPacientById(pacientId);
//        Appointment appointment = appointmentMapper.map(request);
////        appointment.setPacient(pacient);
//
//    }
}
