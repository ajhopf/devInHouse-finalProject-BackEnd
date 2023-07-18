package com.example.labmedical.service;

import com.example.labmedical.repository.TokenRepository;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;

    @Nested
    @DisplayName("Tests of save method")
    class saveMethodTests {
        @Test
        @DisplayName("When user is not found, it should return EntityNotFoundException")
        void test1() {
            User user = User.builder().id(1L).build();

            assertThrows(EntityNotFoundException.class, () -> tokenService.save(Mockito.anyString(), user));
        }

        @Test
        @DisplayName("When user is found, it shouldn't throw an error")
        void test2() {
            User user = User.builder().id(1L).build();

            Mockito.when(userRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(user));

            assertDoesNotThrow(() -> tokenService.save(Mockito.anyString(), user));
        }
    }

}