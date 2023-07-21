package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.response.UserResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;
import com.example.labmedical.controller.mapper.UserMapper;
import com.example.labmedical.controller.mapper.UserResponseMapper;
import com.example.labmedical.enums.Role;

import com.example.labmedical.exceptions.UserException;

import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.User;
import com.example.labmedical.service.auth.TokenService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @Mock
    private UserResponseMapper userResponseMapper;
    @Mock
    private UserMapper userMapper;

    @Nested
    @DisplayName("Tests of getUserIdByEmail method")
    class getUserIdByEmailTests {
        @Test
        @DisplayName("When user is not found, it should throw WrongCredentialsException")
        void test1() {
            assertThrows(WrongCredentialsException.class, () -> userService.getUserIdByEmail(Mockito.anyString()));
        }

        @Test
        @DisplayName("When user is found, it should return an UserIdByEmailResponse object with userId and userEmail")
        void test2() {
            User user = User.builder()
                    .id(1L)
                    .email("email")
                    .build();

            Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                    .thenReturn(Optional.of(user));

            UserIdByEmailResponse result = userService.getUserIdByEmail(Mockito.anyString());

            assertEquals(user.getId(), result.getId());
            assertEquals(user.getEmail(), result.getEmail());
        }
    }

    @Nested
    @DisplayName("Tests of findUserByEmailAndPassword method")
    class findUserByEmailAndPasswordTest {
        @Test
        @DisplayName("When user is not found, it should throw WrongCredentialsException")
        void test1() {
            assertThrows(WrongCredentialsException.class, () -> userService.findUserByEmailAndPassword(Mockito.anyString(), Mockito.anyString()));
        }

        @Test
        @DisplayName("When user is found, it should return an User object with user infos")
        void test2() {
            User user = User.builder()
                    .id(1L)
                    .email("email")
                    .build();

            Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(Optional.of(user));

            User result = userService.findUserByEmailAndPassword(Mockito.anyString(), Mockito.anyString());

            assertEquals(user.getId(), result.getId());
            assertEquals(user.getEmail(), result.getEmail());
        }
    }

    @Nested
    @DisplayName("Tests of findUserByEmail method")
    class findUserByEmailTest {
        @Test
        @DisplayName("When user is not found, it should throw WrongCredentialsException")
        void test1() {
            assertThrows(WrongCredentialsException.class, () -> userService.findUserByEmail(Mockito.anyString()));
        }

        @Test
        @DisplayName("When user is found, it should return an User object with user infos")
        void test2() {
            User user = User.builder()
                    .id(1L)
                    .email("email")
                    .build();

            Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                    .thenReturn(Optional.of(user));

            User result = userService.findUserByEmail(Mockito.anyString());

            assertEquals(user.getId(), result.getId());
            assertEquals(user.getEmail(), result.getEmail());
        }
    }

    @Nested
    @DisplayName("Tests of updateUserPassword method")
    class updateUserPassword {
        @Test
        @DisplayName("When user is not found, it should return EntityNotFoundException with correct message")
        void test1() {
            ResetUserPasswordRequest request = ResetUserPasswordRequest.builder().id(1L).build();

            Exception exception = assertThrows(EntityNotFoundException.class,
                    () -> userService.updateUserPassword(request));

            String expectedMessage = "Usuário não encontrado com id informado";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        @DisplayName("When user is found, it shouldn't throw an error")
        void test2() {
            ResetUserPasswordRequest request = ResetUserPasswordRequest.builder().id(1L).build();

            User user = User.builder()
                    .id(1L)
                    .role(Role.ROLE_ADMIN)
                    .build();

            Mockito.when(userRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(user));

            doNothing().when(tokenService).saveUserToken(Mockito.any(User.class), Mockito.anyString());

            Log log = Log.builder().build();

            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);

            assertDoesNotThrow(() -> userService.updateUserPassword(request));
        }
    }
  
    @Nested
    @DisplayName("Tests of saveUser methods")
    class saveUserMethodTest {
        @Test
        @DisplayName("When user already exists in database, it should throw RegisterDataAlreadyExist")
        void test1() {
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            UserRegisterRequest request = new UserRegisterRequest();
            request.setEmail("example@example.com");
            request.setCpf("123.456.789-23");
            assertThrows(UserException.class, () -> userService.saveUser(request));
        }
        @Test
        @DisplayName("When user doesn't exists in database, it should save user")
        void test2() {
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
            User user = User.builder()
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .build();
            when(userMapper.map(request)).thenReturn(user);
            User result = userService.saveUser(request);
            assertEquals(result.getName(), result.getName());
        }
        @Test
        @DisplayName("When user exists in database, it should return true")
        void test3() {
            UserRegisterRequest request = new UserRegisterRequest();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            request.setEmail("email@example.com");
            request.setCpf("111.222.333-44");
            Boolean result = userService.checkIfUserExist(request);
            assertTrue(result);
        }
        @Test
        @DisplayName("When user exists in database, it should return false")
        void test4() {
            UserRegisterRequest request = new UserRegisterRequest();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
            request.setEmail("email@example.com");
            request.setCpf("111.222.333-44");
            Boolean result = userService.checkIfUserExist(request);
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Tests of checkIfUserExist method")
    class checkIfUserExistTests {
        @Test
        @DisplayName("When user doesn't exist it should return false")
        void test1() {
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .build();

            assertFalse(userService.checkIfUserExist(request));
        }

        @Test
        @DisplayName("When user exists it should return true")
        void test2() {
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .build();

            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                            .thenReturn(true);

            assertTrue(userService.checkIfUserExist(request));
        }
    }

    @Nested
    @DisplayName("Test get user's list feature")
    class findUserListFeatureTest{
        @Test
        @DisplayName("When list is empty, it should return empty exception")
        void test1(){
            Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
            assertThrows(UserException.class, () -> userService.getListUsers());
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
            UserResponse userResponse = UserResponse.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .email("example@example.com")
                    .build();
            when(userResponseMapper.map(user)).thenReturn(userResponse);
            List<UserResponse> response = userService.getListUsers();
            assertTrue(response.size() > 0);
            assertEquals(user.getName(), response.get(0).getName());
        }
    }
    @Nested
    @DisplayName("Test update user feature")
    class updateUserFeatureTest{
        @Test
        @DisplayName("When user not found, it should return exception")
        void test1(){
            UserRegisterRequest request = new UserRegisterRequest();
            UserException userException = assertThrows(UserException.class, () -> userService.updateUser(1l, request));
            assertEquals("Usuário não encontrado", userException.getMessage());
        }
        @Test
        @DisplayName("When user try change the cpf, it should return a execpetion")
        void test2(){
            User user = User.builder()
                    .id(1l)
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_DOCTOR)
                    .build();
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-50")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .build();
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
            UserException userException = assertThrows(UserException.class, () -> userService.updateUser(1l, request));
            assertEquals("CPF não pode ser modificado", userException.getMessage());
        }
        @Test
        @DisplayName("When user try change the role to one not allowed, it should return a execpetion")
        void test3(){
            User user = User.builder()
                    .id(1l)
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_DOCTOR)
                    .build();
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .build();
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
            UserException userException = assertThrows(UserException.class, () -> userService.updateUser(1l, request));
            assertEquals("Não é possível modificar para esse tipo de usuário", userException.getMessage());
        }
        @Test
        @DisplayName("When user update data, it should return a string with success")
        void test4(){
            User user = User.builder()
                    .id(1l)
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_DOCTOR)
                    .build();
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_NURSE)
                    .build();
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
            User newUser = User.builder()
                    .id(1l)
                    .name("André")
                    .gender("Masculino")
                    .telephone("(48) 9 9999-9999")
                    .cpf("111.222.333-44")
                    .email("email@example.com")
                    .password("1234")
                    .role(Role.ROLE_DOCTOR)
                    .build();
            when(userMapper.map(request)).thenReturn(newUser);
            String response = userService.updateUser(1l, request);
            assertEquals("Usuário atualizado com sucesso", response);
        }
    }

    @Nested
    @DisplayName("Test search user feature")
    class searchUserFeatureTest{
        @Test
        @DisplayName("When user is not found, it should throw UserException")
        void test1() {
            assertThrows(UserException.class, () -> userService.userSearch(Mockito.anyLong()));
        }
        @Test
        @DisplayName("When user is not found, it should throw UserException")
        void test2() {
            User user = User.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .email("example@example.com")
                    .build();
            Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            UserResponse userResponse = UserResponse.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .email("example@example.com")
                    .build();
            when(userResponseMapper.map(user)).thenReturn(userResponse);
            UserResponse response = userService.userSearch(user.getId());
            assertEquals(userResponse, response);
        }
    }


    @Nested
    @DisplayName("Test remove user feature")
    class deleteUserFeatureTest{
        @Test
        @DisplayName("When user not find, it should throws exception")
        void test1() {
            assertThrows(UserException.class, () -> userService.deleteUser(Mockito.anyLong()));
        }
        @Test
        @DisplayName("When user is find, it should delete from database")
        void test2() {
            User user = User.builder()
                    .id(1L)
                    .name("André")
                    .password("1234")
                    .role(Role.ROLE_ADMIN)
                    .email("example@example.com")
                    .build();
            Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            String result = userService.deleteUser(1L);
            assertEquals("Usuário removido com sucesso", result);
            Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
            Mockito.verify(logService, Mockito.times(1)).success("Usuário ID: 1 removido");
        }
    }
}

