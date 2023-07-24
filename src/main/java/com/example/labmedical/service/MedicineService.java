package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.MedicineRegisterRequest;
import com.example.labmedical.controller.dtos.response.MedicineResponse;
import com.example.labmedical.controller.mapper.MedicineMapper;
import com.example.labmedical.repository.MedicineRepository;
import com.example.labmedical.repository.model.Medicine;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MedicineResponse> getMedicines(Long pacientId) {
        List<Medicine> medicineList;

        if (pacientId != null) {
            pacientService.getPacientById(pacientId);

            medicineList = medicineRepository.getMedicinesByPacient_Id(pacientId);
            logService.success("Busca de lista de medicamentos do paciente " + pacientId + " realizada.");
        } else {
            medicineList = medicineRepository.findAll();
            logService.success("Busca de lista de medicamentos de todos pacientes realizada.");
        }

        return medicineMapper.map(medicineList);
    }

    public void deleteMedicine(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento com id " + medicineId + " não encontrada."));

        medicineRepository.delete(medicine);
    }

    public MedicineResponse updateMedicine(Long medicineId, MedicineRegisterRequest newMedicine) {
        boolean medicineExists = medicineRepository.existsById(medicineId);

        if(!medicineExists) {
            throw new EntityNotFoundException(String.format("Medicamento id: %d não encontrado",medicineId));
        }
        Medicine currentMedicine = medicineRepository.findById(medicineId).get();
        medicineMapper.update(currentMedicine,newMedicine);

        medicineRepository.save(currentMedicine);
        logService.success(String.format("O medicamento id: %d foi atualiza", medicineId));
        MedicineResponse response = medicineMapper.map(currentMedicine);
        return response;
    }

}
