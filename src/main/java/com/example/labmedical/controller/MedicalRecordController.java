package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.controller.dtos.response.PatientMedicalRecordResponse;
import com.example.labmedical.controller.mapper.PacientMapper;
import com.example.labmedical.repository.model.Pacient;
import com.example.labmedical.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
public class MedicalRecordController {
    @Autowired
    private PacientService patientService;
    @Autowired
    private PacientMapper pacientMapper;
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<PatientMedicalRecordResponse> getMedicalRecordPatient(
            @PathVariable Long id) {
        return ResponseEntity.ok(pacientMapper.map(patientService.getPacientResponseById(id)));
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<List<PatientMedicalRecordResponse>> getAllMedicalRecords() {
        var allRecords = patientService.getPacients();
        return ResponseEntity.ok(allRecords.parallelStream().map(pacientMapper::map).toList());
    }
}
