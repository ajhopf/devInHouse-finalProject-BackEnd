package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.service.PacientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/pacientes")
public class PacientController {
    @Autowired
    private PacientService pacientService;

    @PostMapping
    public ResponseEntity<PacientResponse> registerPacient(
            @RequestBody @Valid PacientRegisterRequest pacientRegisterRequest,
            UriComponentsBuilder uriBuilder
    ) {
        PacientResponse pacient = pacientService.registerPacient(pacientRegisterRequest);

        URI uri = uriBuilder.path("/api/pacients/{id}")
                .buildAndExpand(pacient.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pacient);
    }

}
