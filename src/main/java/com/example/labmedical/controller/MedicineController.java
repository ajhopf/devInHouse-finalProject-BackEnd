package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.MedicineRegisterRequest;
import com.example.labmedical.controller.dtos.response.MedicineResponse;
import com.example.labmedical.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping()
    public ResponseEntity<MedicineResponse> registerMedicine(@RequestBody @Valid MedicineRegisterRequest request, UriComponentsBuilder uriBuilder) {
        MedicineResponse medicine = medicineService.registerMedicine(request);

        URI uri = uriBuilder.path("/api/medicamentos/{id}")
                .buildAndExpand(medicine.getId())
                .toUri();

        return ResponseEntity.created(uri).body(medicine);
    }

    @GetMapping
    public ResponseEntity<List<MedicineResponse>> getMedicines(@RequestParam(required = false) Long pacientId) {
        List<MedicineResponse> medicineResponseList = medicineService.getMedicines(pacientId);

        return ResponseEntity.ok(medicineResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);

        return ResponseEntity.noContent().build();
    }

}
