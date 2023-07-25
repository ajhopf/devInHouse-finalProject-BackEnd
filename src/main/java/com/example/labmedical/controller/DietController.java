package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.DietRegisterRequest;
import com.example.labmedical.controller.dtos.request.DietUpdateRequest;
import com.example.labmedical.controller.dtos.response.DietResponse;
import com.example.labmedical.service.DietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dietas")
public class DietController {
    @Autowired
    private DietService dietService;
    @GetMapping
    public ResponseEntity<List<DietResponse>> getDiets(
            @RequestParam(required = false) String pacientName
    ) {
        List<DietResponse> dietList = dietService.getDiets(pacientName);

        return ResponseEntity.ok(dietList);
    }

    @PostMapping
    public ResponseEntity<DietResponse> registerDiet(
            @RequestBody @Valid DietRegisterRequest dietRegisterRequest,
            UriComponentsBuilder uriBuilder
            ) {
        DietResponse diet = dietService.registerDiet(dietRegisterRequest);

        URI uri = uriBuilder.path("api/dietas/{id}")
                .buildAndExpand(diet.getId())
                .toUri();

        return ResponseEntity.created(uri).body(diet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiet(@PathVariable Long id) {
        dietService.deleteDiet(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietResponse> updateDiet(
            @PathVariable Long id,
            @RequestBody @Valid DietUpdateRequest request
            ) {
        DietResponse diet = dietService.updateDiet(id, request);

        return ResponseEntity.ok(diet);
    }
}
