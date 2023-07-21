package com.example.labmedical.service;

import com.example.labmedical.repository.SpecialCareRepository;
import com.example.labmedical.repository.model.Pacient;
import com.example.labmedical.repository.model.SpecialCare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialCareService {
    @Autowired
    private SpecialCareRepository specialCareRepository;
    @Autowired
    private LogService logService;

    public void registerSpecialCare(Pacient pacient, String specialCareDescription) {
        SpecialCare specialCare = SpecialCare.builder()
                .pacient(pacient)
                .specialCare(specialCareDescription)
                .build();

        specialCareRepository.save(specialCare);

        String logDescription = "Cuidado Especial. Descrição: " + specialCareDescription + "; Id paciente: " + pacient.getId();

        logService.success(logDescription);
    }

    public void deleteAllPacientSpecialCares(Long pacientId) {
        List<SpecialCare> specialCares = specialCareRepository.getSpecialCaresByPacient_Id(pacientId);

        specialCareRepository.deleteAll(specialCares);
    }

    public List<String> getAllPacientSpecialCares(Long pacientId) {
        return specialCareRepository.getSpecialCaresByPacient_Id(pacientId)
                .stream()
                .map(SpecialCare::getSpecialCare)
                .toList();
    }

}
