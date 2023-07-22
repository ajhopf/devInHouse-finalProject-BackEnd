package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.service.ConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")

public class ConfigController {
    @Autowired
    private ConfigService configService;

    @PostMapping ("/sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createSystem(
           @Valid @RequestBody SystemConfigRequest config
    ) throws JsonProcessingException {
        configService.saveSystemConfig(config);
        return ResponseEntity.ok().build();
    }

}
