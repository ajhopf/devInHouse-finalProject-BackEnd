package com.example.labmedical.controller;


import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios/")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> userLogin(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
            ) {
        AuthenticationResponse response = userService.loginUser(authenticationRequest);

        return ResponseEntity.ok(response);
    }
}
