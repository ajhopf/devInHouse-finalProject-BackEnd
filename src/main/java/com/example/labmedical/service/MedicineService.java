package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.MedicineRegisterRequest;
import com.example.labmedical.controller.dtos.response.MedicineResponse;
import com.example.labmedical.controller.mapper.MedicineMapper;
import com.example.labmedical.repository.MedicineRepository;
import com.example.labmedical.repository.model.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    @Autowired
    private LogService logService;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineMapper medicineMapper;

    public MedicineResponse registerMedicine(MedicineRegisterRequest request) {
        pacientService.getPacientById(request.getPacientId());
        Medicine medicine = medicineMapper.map(request);
        medicineRepository.save(medicine);

        logService.success("Medicação registrada. Id medicação: " + medicine.getId() + "; Id Paciente: " + medicine.getPacient().getId());

        return medicineMapper.map(medicine);
    }

}
