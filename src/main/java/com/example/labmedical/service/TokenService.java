package com.example.labmedical.service;

import com.example.labmedical.enums.TokenType;
import com.example.labmedical.repository.TokenRepository;
import com.example.labmedical.repository.model.Token;
import com.example.labmedical.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public void save(String token, User user) {
        tokenRepository.save(
                Token.builder()
                        .token(token)
                        .expired(false)
                        .revoked(false)
                        .tokenType(TokenType.BEARER)
                        .user(user)
                        .build()
        );
    }
}
