package com.example.labmedical.service.auth;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.controller.dtos.response.AuthenticationResponse;
import com.example.labmedical.enums.Role;
import com.example.labmedical.service.LogService;
import com.example.labmedical.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LogService logService;

    public AuthenticationResponse register(UserRegisterRequest request, Role role) {
        var savedUser = userService.saveUser(request);
        var jwtToken = jwtService.generateToken(savedUser);
        tokenService.saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .photoUrl(savedUser.getPhotoUrl())
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService
                .findUserByEmailAndPassword(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);

        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);
        var response = AuthenticationResponse.builder()
                .name(user.getName())
                .role(user.getRole())
                .photoUrl(user.getPhotoUrl())
                .accessToken(jwtToken)
                .build();


        String logDescription = "O(a) " + response.getRole().toString().substring(5) + " " + response.getName() + " efetuou login no sistema.";
        logService.success(logDescription);

        return response;
    }

}
