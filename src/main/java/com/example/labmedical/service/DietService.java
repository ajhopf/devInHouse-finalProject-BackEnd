package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.DietRegisterRequest;
import com.example.labmedical.controller.dtos.request.DietUpdateRequest;
import com.example.labmedical.controller.dtos.response.DietResponse;
import com.example.labmedical.controller.mapper.DietMapper;
import com.example.labmedical.repository.DietRepository;
import com.example.labmedical.repository.model.Diet;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteDiet(Long id) {
        dietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dieta com id " + id + " não encontrada."));

        dietRepository.deleteById(id);

        logService.success("Dieta deletada. Id: " + id);
    }

    public List<DietResponse> getDiets(String requestParam) {
        List<Diet> dietList;

        if (requestParam != null && isPositiveNumber(requestParam)) {
            Long pacientId = Long.parseLong(requestParam);
            dietList = dietRepository.findAllByPacient_Id(pacientId);
        } else if (requestParam != null && requestParam.length() > 0) {
            dietList = dietRepository.findAllByPacient_NameContaining(requestParam);
        } else {
            dietList = dietRepository.findAll();
        }

        logService.success("Busca de lista de dietas realizada.");
        return dietMapper.map(dietList);
    }

    public DietResponse updateDiet(Long dietId, DietUpdateRequest request) {
        Diet oldDiet = dietRepository.findById(dietId)
                .orElseThrow(() -> new EntityNotFoundException("Dieta com id " + dietId + " não encontrada."));

        Diet newDiet = dietMapper.map(request);
        newDiet.setPacient(oldDiet.getPacient());
        newDiet.setStatus(oldDiet.getStatus());
        newDiet.setId(dietId);

        dietRepository.save(newDiet);

        logService.success("Dieta atualizada. Id: " + dietId);

        return dietMapper.map(newDiet);
    }

    public boolean isPositiveNumber(String param) {
        return param.matches("\\d+");
    }
}
