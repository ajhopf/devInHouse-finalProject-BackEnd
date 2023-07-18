package com.example.labmedical.controller;


import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;
import com.example.labmedical.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{email}")
    public ResponseEntity<UserIdByEmailResponse> getUserIdByEmail(@PathVariable @Valid String email) {
        UserIdByEmailResponse response = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(response);
    }

    @PutMapping("resetarsenha")
    public ResponseEntity<Void> resetUserPassword(
            @RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        userService.updateUserPassword(resetUserPasswordRequest);

        return ResponseEntity.ok().build();
    }

}
