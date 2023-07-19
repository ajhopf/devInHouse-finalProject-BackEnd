package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.controller.mapper.PacientMapper;
import com.example.labmedical.exceptions.PacientAlreadyRegisteredException;
import com.example.labmedical.repository.PacientRepository;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.repository.model.Pacient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PacientResponse registerPacient(PacientRegisterRequest request) {
        Boolean pacientExists = checkIfPacientExists(request);

        if (pacientExists) {
            throw new PacientAlreadyRegisteredException("Paciente não cadastrado. Cpf ou email já estão em uso.");
        }

        AddressRegisterRequest pacientAddressToRegister = request.getAddress();
        Address addressWithId = addressService.registerAdress(pacientAddressToRegister);

        Pacient pacient = Pacient.builder()
                .email(request.getEmail())
                .dob(request.getDob())
                .cpf(request.getCpf())
                .rg(request.getRg())
                .gender(request.getGender())
                .name(request.getName())
                .phone(request.getPhone())
                .civilStatus(request.getCivilStatus())
                .emergencyContact(request.getEmergencyContact())
                .healthInsurance(request.getHealthInsurance())
                .nationality(request.getNationality())
                .healthInsuranceExpirationDate(request.getHealthInsuranceExpirationDate())
                .address(addressWithId)
                .build();

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

    public Boolean checkIfPacientExists(PacientRegisterRequest request) {
        return pacientRepository.existsByEmailOrCpf(request.getEmail(), request.getCpf());
    }
}
