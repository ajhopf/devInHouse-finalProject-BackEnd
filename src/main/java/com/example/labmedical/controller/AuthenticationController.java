package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.response.AuthenticationResponse;
import com.example.labmedical.service.auth.AuthenticationService;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("cadastrar")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody UserRegisterRequest request,
            @RequestParam Role role
    ) {
        return ResponseEntity.ok(authenticationService.register(request, role));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
