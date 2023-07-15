package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.Token;
import com.example.labmedical.repository.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public AuthenticationResponse findUserByEmailAndPassword(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        User user = userRepository.findByEmailAndPassword(email, password).orElseThrow(WrongCredentialsException::new);

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

        return response;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String saveUser(UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .name(userRegisterRequest.getName())
                .email(userRegisterRequest.getEmail())
                .role(userRegisterRequest.getRole())
                .password(userRegisterRequest.getPassword())
                .gender(userRegisterRequest.getGender())
                .cpf(userRegisterRequest.getCpf())
                .telephone(userRegisterRequest.getTelephone())
                .build();
        userRepository.save(user);
        return "Usuário criado com sucesso";
    }
}
