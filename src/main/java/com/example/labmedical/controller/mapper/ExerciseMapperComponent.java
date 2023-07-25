package com.example.labmedical.controller.mapper;

import com.example.labmedical.exceptions.PatientNotFountException;
import com.example.labmedical.repository.PacientRepository;
import com.example.labmedical.repository.model.Pacient;
import com.example.labmedical.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ExerciseMapperComponent {
    @Autowired
    private PacientRepository pacientRepository;
    Pacient pacientIdToPacient(Long id) {
        if(id!=null){
            Pacient pacient =  pacientRepository.findById(id).orElseThrow(() -> {
                return new PatientNotFountException("Patiente nao encontrado");
            });
            if(Boolean.FALSE.equals(pacient.getIsActive())) {
                throw new PatientNotFountException("Patiente inativo");
            }
            return pacient;
        }
        return null;
    }

    LocalTime localTimeStringToLocalTimeObject(String localTimeString) {
        if (localTimeString != null) {
            return LocalTime.parse(localTimeString, DateTimeFormatter.ofPattern("[H:]mm[:ss]"));
        }
        return null;
    }

    String localTimeObjectToLocalTimeString(LocalTime localTime) {
        if (localTime != null) {
            return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return null;
        }
    }

}
