package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.response.AuthenticationResponse;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TokenService tokenService;

    public AuthenticationResponse loginUser(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        User user = this.findUserByEmailAndPassword(email, password);

        String token = Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .name(user.getName())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole())
                .token(token)
                .build();

        String logDescription = "O(a) " + response.getRole().toString().substring(5) + " " + response.getName() + " efetuou login no sistema.";

        tokenService.save(token, user);

        logService.success(logDescription);

        return response;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new WrongCredentialsException("Email ou senha informados não conferem ou não existem."));
    }

    public UserIdByEmailResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new WrongCredentialsException("Email informado não encontrado."));

        return UserIdByEmailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void updateUserPassword(ResetUserPasswordRequest resetUserPasswordRequest) {
        User user = userRepository.findById(resetUserPasswordRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id informado"));

        user.setPassword(resetUserPasswordRequest.getPassword());
        userRepository.save(user);

        String logDescription = "O(a) " + user.getRole().toString().substring(5) + " " + user.getName() + " resetou a senha.";
        logService.success(logDescription);
    }
}
