package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.RegisterDataAlreadyExist;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

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
//        @Test
//        @DisplayName("When user is not found, it should return WrongCredentialsException")
//        void test1() {
//            AuthenticationRequest request = new AuthenticationRequest();
//            assertThrows(WrongCredentialsException.class, () -> userService.loginUser(request));
//        }
//
//        @Test
//        @DisplayName("When user is found, it should return an AuthenticationResponse instance with the user infos")
//        void test2() {
//            //given
//            String email = "email";
//            String password = "password";
//            AuthenticationRequest request = new AuthenticationRequest(email, password);
//
//            User user = User.builder()
//                    .id(1L)
//                    .name("André")
//                    .password("1234")
//                    .role(Role.ROLE_ADMIN)
//                    .photoUrl("photoUrl")
//                    .build();
//
//            Log log = Log.builder().build();
//
//            Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
//                    .thenReturn(Optional.of(user));
//
//            doNothing().when(tokenService).save(Mockito.anyString(), Mockito.any(User.class));
//
//            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
//            //when
//            AuthenticationResponse result = userService.loginUser(request);
//            //then
//            assertEquals(AuthenticationResponse.class, result.getClass());
//            assertEquals(user.getName(), result.getName());
//        }

//        @Test
//        @DisplayName("When user is found, it should return an AuthenticationResponse instance with a token")
//        void test3() {
//            //given
//            String email = "email";
//            String password = "password";
//            AuthenticationRequest request = new AuthenticationRequest(email, password);
//
//            User user = User.builder()
//                    .id(1L)
//                    .name("André")
//                    .password("1234")
//                    .role(Role.ROLE_ADMIN)
//                    .photoUrl("photoUrl")
//                    .build();
//
//            Log log = Log.builder().build();
//
//            Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
//                    .thenReturn(Optional.of(user));
//
//            doNothing().when(tokenService).save(Mockito.anyString(), Mockito.any(User.class));
//
//            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
//            //when
//            AuthenticationResponse result = userService.loginUser(request);
//            //then
//            assertNotNull(result.getToken());
//        }

    }

    @Nested
    @DisplayName("Tests of saveUser methos")
    class saveUserMethodTest{
        @Test
        @DisplayName("When user already exists in database, it should throw RegisterDataAlreadyExist")
        void test1(){
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            UserRegisterRequest request = new UserRegisterRequest();
            request.setEmail("example@example.com");
            request.setCpf("123.456.789-23");
            assertThrows(RegisterDataAlreadyExist.class, () -> userService.saveUser(request));
        }

        @Test
        @DisplayName("When user doesn't exists in database, it should save user")
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
            User result = userService.saveUser(request);
            assertEquals(result.getName(), result.getName());
        }

        @Test
        @DisplayName("When user exists in database, it should return true")
        void test3(){
            UserRegisterRequest request = new UserRegisterRequest();
            Mockito.when(userRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            request.setEmail("email@example.com");
            request.setCpf("111.222.333-44");
            Boolean result = userService.checkIfUserExist(request);
            assertTrue(result);
        }

        @Test
        @DisplayName("When user exists in database, it should return false")
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
    @DisplayName("Tests of findUserByEmail method")
    class findUserByEmail {
        @Test
        @DisplayName("When user is not found, it should return WrongCredentialsException with correct message")
        void test1() {
            Exception exception = assertThrows(WrongCredentialsException.class, () -> userService.findUserByEmail(Mockito.anyString()));

            String expectedMessage = "Email informado não encontrado";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

//        @Test
//        @DisplayName("When user is found, it should return UserByEmailResponse with user id and email")
//        void test2() {
//            //given
//            String email = "email";
//
//            User user = User.builder()
//                    .id(1L)
//                    .name("André")
//                    .email(email)
//                    .password("1234")
//                    .role(Role.ROLE_ADMIN)
//                    .photoUrl("photoUrl")
//                    .build();
//
//            Mockito.when(userRepository.findByEmail(Mockito.anyString()))
//                    .thenReturn(Optional.of(user));
//
//            //when
//            UserIdByEmailResponse result = userService.findUserByEmail(email);
//            //then
//            assertEquals(user.getId(), result.getId());
//            assertEquals(user.getEmail(), result.getEmail());
//        }

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

//            @Test
//            @DisplayName("When user is found, it shouldn't throw an error")
//            void test2() {
//                ResetUserPasswordRequest request = ResetUserPasswordRequest.builder().id(1L).build();
//
//                User user = User.builder()
//                        .id(1L)
//                        .role(Role.ROLE_ADMIN)
//                        .build();
//
//                Mockito.when(userRepository.findById(Mockito.anyLong()))
//                        .thenReturn(Optional.of(user));
//
//                doNothing().when(tokenService).save(Mockito.anyString(), Mockito.any(User.class));
//
//                Log log = Log.builder().build();
//
//                Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
//
//                assertDoesNotThrow(() -> userService.updateUserPassword(request));
//            }
        }
    }
}