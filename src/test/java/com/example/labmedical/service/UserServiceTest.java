package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class UserServiceTest {
    @Mock
    private LogService logService;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("Tests of loginUser method")
    class loginUserMethodTests {
        @Test
        @DisplayName("When user is not found, it should return WrongCredentialsException")
        void test1() {
            AuthenticationRequest request = new AuthenticationRequest();
            assertThrows(WrongCredentialsException.class, () -> userService.loginUser(request));
        }

        @Test
        @DisplayName("When user is found, it should return an AuthenticationResponse instance with the user infos")
        void test2() {
            //given
            String email = "email";
            String password = "password";
            AuthenticationRequest request = new AuthenticationRequest(email, password);

            User user = User.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .photoUrl("photoUrl")
                    .build();

            Log log = Log.builder().build();

            Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(Optional.of(user));

            doNothing().when(tokenService).save(Mockito.anyString(), Mockito.any(User.class));

            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
            //when
            AuthenticationResponse result = userService.loginUser(request);
            //then
            assertEquals(AuthenticationResponse.class, result.getClass());
            assertEquals(user.getName(), result.getName());
        }

        @Test
        @DisplayName("When user is found, it should return an AuthenticationResponse instance with a token")
        void test3() {
            //given
            String email = "email";
            String password = "password";
            AuthenticationRequest request = new AuthenticationRequest(email, password);

            User user = User.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .photoUrl("photoUrl")
                    .build();

            Log log = Log.builder().build();

            Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(Optional.of(user));

            doNothing().when(tokenService).save(Mockito.anyString(), Mockito.any(User.class));

            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
            //when
            AuthenticationResponse result = userService.loginUser(request);
            //then
            assertNotNull(result.getToken());
        }
    }
}