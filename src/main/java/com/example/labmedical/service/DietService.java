package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.DietRegisterRequest;
import com.example.labmedical.controller.dtos.response.DietResponse;
import com.example.labmedical.controller.mapper.DietMapper;
import com.example.labmedical.repository.DietRepository;
import com.example.labmedical.repository.model.Diet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietService {
    @Autowired
    private DietRepository dietRepository;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private DietMapper dietMapper;
    @Autowired
    private LogService logService;

    public DietResponse registerDiet(DietRegisterRequest dietRegisterRequest) {
        pacientService.getPacientById(dietRegisterRequest.getPacientId());

        Diet diet = dietMapper.map(dietRegisterRequest);
        dietRepository.save(diet);

        logService.success("Dieta criada. Id paciente: " + diet.getPacient().getId() + ". Id dieta: " + diet.getId());

        return dietMapper.map(diet);
    }
}
