package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.mapper.AppointmentMapper;
import com.example.labmedical.repository.AppointmentRepository;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Medicine;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private LogService logService;

    public List<AppointmentResponse> getAppointments(Long pacientId) {
        List<Appointment> appointmentList;

        if (pacientId != null) {
            pacientService.getPacientById(pacientId);

            appointmentList = appointmentRepository.getAppointmentsByPacient_Id(pacientId);
            logService.success("Busca de lista de consultas do paciente " + pacientId + " realizada.");
        } else {
            appointmentList = appointmentRepository.findAll();
            logService.success("Busca de lista de consultas de todos pacientes realizada.");
        }

        return appointmentMapper.map(appointmentList);
    }

    public AppointmentResponse registerAppointment(AppointmentRegisterRequest request) {
        pacientService.getPacientById(request.getPacientId());

        Medicine medicine = null;
        if (request.getMedicineId() != null) {
            medicine = medicineService.getMedicineById(request.getMedicineId());
        }

        Appointment appointment = appointmentMapper.map(request);
        appointment.setMedicine(medicine);
        appointmentRepository.save(appointment);

        logService.success("Consulta registrada. Id consulta: " + appointment.getId() + "; Id Paciente: " + appointment.getPacient().getId());

        return appointmentMapper.map(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Consulta com id " + appointmentId + " não encontrada."));

        appointmentRepository.delete(appointment);

        logService.success("Consulta deletada. Id consulta: " + appointmentId);
    }

    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRegisterRequest updatedAppointment) {
        boolean appointmentExists = appointmentRepository.existsById(appointmentId);

        if(!appointmentExists) {
            throw new EntityNotFoundException(String.format("Consulta id: %d não encontrado",appointmentId));
        }

        pacientService.getPacientById(updatedAppointment.getPacientId());

        Medicine medicine = null;
        if (updatedAppointment.getMedicineId() != null) {
            medicine = medicineService.getMedicineById(updatedAppointment.getMedicineId());
        }

        Appointment appointment = appointmentMapper.map(updatedAppointment);
        appointment.setMedicine(medicine);
        appointment.setId(appointmentId);

        appointmentRepository.save(appointment);

        logService.success(String.format("A consulta id: %d foi atualiza", appointmentId));
        AppointmentResponse response = appointmentMapper.map(appointment);
        return response;
    }
}
