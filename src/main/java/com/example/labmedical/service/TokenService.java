package com.example.labmedical.service;

import com.example.labmedical.enums.TokenType;
import com.example.labmedical.repository.TokenRepository;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.Token;
import com.example.labmedical.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(String token, User user) {
        User existentUser = userRepository.findById(user.getId())
                .orElseThrow(EntityNotFoundException::new);

        tokenRepository.save(
                Token.builder()
                        .token(token)
                        .expired(false)
                        .revoked(false)
                        .tokenType(TokenType.BEARER)
                        .user(existentUser)
                        .build()
        );
    }
}
