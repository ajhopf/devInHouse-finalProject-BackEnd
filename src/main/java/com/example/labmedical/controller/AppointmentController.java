package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AppointmentResponse> registerAppointment(
            @RequestBody @Valid AppointmentRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        AppointmentResponse appointment = appointmentService.registerAppointment(request);

        URI uri = uriBuilder.path("/api/consultas/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();

        return ResponseEntity.created(uri).body(appointment);
    }
}
