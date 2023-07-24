package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.controller.dtos.response.SystemConfigResponse;
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
    public ResponseEntity<Void> createSystemConfig(
           @Valid @RequestBody SystemConfigRequest config
    ) throws JsonProcessingException {
        configService.saveSystemConfig(config);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/sistema")
    public ResponseEntity<SystemConfigResponse> getSystemConfig(
    ) throws JsonProcessingException {
        return ResponseEntity.ok().body(configService.getSystemConfig());
    }

    @PostMapping ("/sistema/resetar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> resetSystemConfig(
    ){
        configService.resetSystemConfig();
        return ResponseEntity.ok().build();
    }
}
