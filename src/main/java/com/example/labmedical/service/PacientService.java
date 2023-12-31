package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientUpdateRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.controller.mapper.PacientMapper;
import com.example.labmedical.exceptions.PacientAlreadyRegisteredException;
import com.example.labmedical.exceptions.PatientWithRecordsException;
import com.example.labmedical.repository.*;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.repository.model.Pacient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacientService {
    @Autowired
    private PacientRepository pacientRepository;
    @Autowired
    private PacientMapper pacientMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AlergyService alergyService;
    @Autowired
    private SpecialCareService specialCareService;
    @Autowired
    private LogService logService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private DietRepository dietRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<PacientResponse> getPacients() {
        List<Pacient> pacientList = pacientRepository.findAll();
        List<PacientResponse> pacientResponseList = new ArrayList<>();

        for (Pacient pacient : pacientList) {
            PacientResponse pacientResponse = pacientMapper.map(pacient);
            pacientResponse.setSpecialCare(specialCareService.getAllPacientSpecialCares(pacient.getId()));
            pacientResponse.setAlergies(alergyService.getAllPacientAlergies(pacient.getId()));
            pacientResponseList.add(pacientResponse);
        }

        logService.success("Busca de todos pacientes realizada.");

        return pacientResponseList;
    }


    public PacientResponse getPacientResponseById(Long id) {
        Pacient pacient = pacientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com id " + id + " não encontrado."));

        PacientResponse pacientResponse = pacientMapper.map(pacient);

        List<String> pacientAlergies = alergyService.getAllPacientAlergies(id);
        List<String> pacientSpecialCares = specialCareService.getAllPacientSpecialCares(id);
        pacientResponse.setAlergies(pacientAlergies);
        pacientResponse.setSpecialCare(pacientSpecialCares);

        logService.success("Busca pelo paciente com id " + id + " realizada.");

        return pacientResponse;
    }

    public Pacient getPacientById(Long id) {
        return pacientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com id " + id + " não encontrado."));
    }

    public PacientResponse registerPacient(PacientRegisterRequest request) {
        Boolean pacientExists = checkIfPacientExists(request);

        if (pacientExists) {
            throw new PacientAlreadyRegisteredException("Paciente não cadastrado. Cpf ou email já estão em uso.");
        }

        Pacient pacient = pacientMapper.map(request);

        addressService.registerAdress(pacient.getAddress());

        pacient = pacientRepository.save(pacient);

        if (request.getAlergies() != null && request.getAlergies().size() > 0) {
            List<String> alergies = request.getAlergies();

            for (String alergy : alergies) {
                alergyService.registerAlergy(pacient, alergy);
            }
        }

        if (request.getSpecialCare() != null && request.getSpecialCare().size() > 0) {
            List<String> specialCareList = request.getSpecialCare();

            for (String specialCare : specialCareList) {
                specialCareService.registerSpecialCare(pacient, specialCare);
            }
        }

        PacientResponse response = pacientMapper.map(pacient);
        response.setAlergies(request.getAlergies());
        response.setSpecialCare(request.getSpecialCare());

        logService.success("Paciente registrado. Id: " + response.getId());

        return response;
    }

    public PacientResponse updatePacient(PacientUpdateRequest request, Long id) {
        Pacient pacient = pacientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com id " + id + " não encontrado."));

        if (request.getAlergies() != null && request.getAlergies().size() > 0) {
            alergyService.deleteAllPacientAlergies(id);

            for (String alergy : request.getAlergies()) {
                alergyService.registerAlergy(pacient, alergy);
            }
        }

        if (request.getSpecialCare() != null && request.getSpecialCare().size() > 0) {
            specialCareService.deleteAllPacientSpecialCares(id);

            for (String specialCare : request.getSpecialCare()) {
                specialCareService.registerSpecialCare(pacient, specialCare);
            }
        }

        Address newAddress = addressService.updateAddressById(pacient.getAddress().getId(), request.getAddress());

        Pacient pacientWithNewInfos = pacientMapper.map(request);
        pacientWithNewInfos.setId(id);
        pacientWithNewInfos.setAddress(newAddress);
        pacientWithNewInfos.setCpf(pacient.getCpf());
        pacientWithNewInfos.setRg(pacient.getRg());

        pacientRepository.save(pacientWithNewInfos);

        PacientResponse response = pacientMapper.map(pacientWithNewInfos);
        response.setAlergies(request.getAlergies());
        response.setSpecialCare(request.getSpecialCare());

        logService.success("Paciente atualizado. Id: " + id);

        return response;
    }

    public Boolean checkIfPacientExists(PacientRegisterRequest request) {
        return pacientRepository.existsByEmailOrCpf(request.getEmail(), request.getCpf());
    }

    public void deletePacient(Long id) {
        Pacient pacient = pacientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com id " + id + " não encontrado."));

        if (hasRecords(id)) {
            throw new PatientWithRecordsException();
        }

        alergyService.deleteAllPacientAlergies(pacient.getId());
        specialCareService.deleteAllPacientSpecialCares(pacient.getId());
        pacientRepository.delete(pacient);
        addressService.deleteAddress(pacient.getAddress());

        logService.success("Paciente deletado. Id: " + id);
    }

    public boolean hasRecords(Long id) {
        return appointmentRepository.existsByPacient_Id(id) ||
               examRepository.existsByPacient_Id(id) ||
               medicineRepository.existsByPacient_Id(id) ||
               dietRepository.existsByPacient_Id(id) ||
               exerciseRepository.existsByPatient_Id(id);
    }
}
