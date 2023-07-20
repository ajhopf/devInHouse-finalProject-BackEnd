package com.example.labmedical.service;

import com.example.labmedical.repository.AlergyRepository;
import com.example.labmedical.repository.model.Alergy;
import com.example.labmedical.repository.model.Pacient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlergyService {
    @Autowired
    private AlergyRepository alergyRepository;

    @Autowired
    private LogService logService;

    public void registerAlergy(Pacient pacient, String alergyDescription) {
        Alergy alergy = Alergy.builder()
                .pacient(pacient)
                .alergy(alergyDescription)
                .build();

        alergyRepository.save(alergy);

        String logDescription = "Alergia registrada. Descrição: " + alergyDescription + "; Id paciente: " + pacient.getId();

        logService.success(logDescription);
    }

    public void deleteAllPacientAlergies(Long pacientId) {
        List<Alergy> pacientAlergies = alergyRepository.getAlergiesByPacient_Id(pacientId);

        alergyRepository.deleteAll(pacientAlergies);
    }

    public List<String> getAllPacientAlergies(Long pacientId) {
        return alergyRepository.getAlergiesByPacient_Id(pacientId)
                .stream()
                .map(Alergy::getAlergy)
                .toList();
    }
}
