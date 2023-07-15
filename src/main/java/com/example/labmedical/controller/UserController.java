package com.example.labmedical.controller;


import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.repository.model.User;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.repository.model.User;
import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.response.AuthenticationResponse;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;
import com.example.labmedical.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{email}")
    public ResponseEntity<UserIdByEmailResponse> getUserIdByEmail(@PathVariable @Valid String email) {
        UserIdByEmailResponse response = userService.findUserByEmail(email);

        return ResponseEntity.ok(response);
    }

    @PutMapping("resetarsenha")
    public ResponseEntity<Void> resetUserPassword(
            @RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        userService.updateUserPassword(resetUserPasswordRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("cadastrar")
    public ResponseEntity<String> userRegister(
            @RequestBody @Valid UserRegisterRequest userRegisterRequest
    ) {
        String response = userService.saveUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
