package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.controller.dtos.request.UserListResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.EmptyUserListException;
import com.example.labmedical.exceptions.RegisterAlreadyExistExcepetion;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

    @Nested
    @DisplayName("Tests of saveUser method")
    class saveUserMethodTest{
        @Test
        @DisplayName("When user already exist in database, it should throw RegisterDataAlreadyExist")
        void test1(){
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            UserRegisterRequest request = new UserRegisterRequest();
            request.setEmail("example@example.com");
            request.setCpf("123.456.789-23");
            assertThrows(RegisterAlreadyExistExcepetion.class, () -> userService.saveUser(request));
        }

        @Test
        @DisplayName("When user not exist in database, it should save user")
        void test2(){
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .build();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
            String result = userService.saveUser(request);
            assertEquals("Usuário criado com sucesso", result);
        }

        @Test
        @DisplayName("When user exist in database, it should return true")
        void test3(){
            UserRegisterRequest request = new UserRegisterRequest();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            request.setEmail("email@example.com");
            request.setCpf("111.222.333-44");
            Boolean result = userService.checkIfUserExist(request);
            assertTrue(result);
        }

        @Test
        @DisplayName("When user exist in database, it should return false")
        void test4(){
            UserRegisterRequest request = new UserRegisterRequest();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
            request.setEmail("email@example.com");
            request.setCpf("111.222.333-44");
            Boolean result = userService.checkIfUserExist(request);
            assertFalse(result);
        }
    }
    @Nested
    @DisplayName("Test get user's list method")
    class finUserListMethodTest{
        @Test
        @DisplayName("When list is empty, it should return empty exception")
        void test1(){
            Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
            assertThrows(EmptyUserListException.class, () -> userService.getListUsers());
        }

        @Test
        @DisplayName("When list not empty, shoul return a list")
        void test2(){
            User user = User.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .email("example@example.com")
                    .build();
            List<User> list = new ArrayList<>();
            list.add(user);
            Mockito.when(userRepository.findAll()).thenReturn(list);
            List<UserListResponse> response = userService.getListUsers();
            assertTrue(response.size() > 0);
            assertEquals(user.getName(), response.get(0).getName());
        }
    }
}