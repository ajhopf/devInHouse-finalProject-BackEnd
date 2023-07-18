package com.example.labmedical.service.auth;

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

    public void saveUserToken(User user, String jwtToken) {
        userRepository.findById(user.getId())
                .orElseThrow(EntityNotFoundException::new);

        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
