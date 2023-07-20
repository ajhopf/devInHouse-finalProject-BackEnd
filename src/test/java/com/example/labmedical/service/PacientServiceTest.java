package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.controller.mapper.PacientMapper;
import com.example.labmedical.exceptions.PacientAlreadyRegisteredException;
import com.example.labmedical.repository.PacientRepository;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.Pacient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class PacientServiceTest {
    @Mock
    private PacientRepository pacientRepository;
    @Mock
    private PacientMapper pacientMapper;
    @Mock
    private AddressService addressService;
    @Mock
    private LogService logService;
    @Mock
    private AlergyService alergyService;
    @Mock
    private SpecialCareService specialCareService;
    @InjectMocks
    private PacientService pacientService;

    @Nested
    @DisplayName("Tests of registerPacient method")
    class registerPacientTests {
        @Test
        @DisplayName("When pacient with same cpf or email is found, it should throw PacientAlreadyRegisteredException")
        void test1() {
            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                            .thenReturn(true);

            assertThrows(PacientAlreadyRegisteredException.class,
                    () -> pacientService.registerPacient(request)
            );
        }

        @Test
        @DisplayName("When no pacient with given cpf or email is found, it should save Pacient and return Pacient with id")
        void test2() {
            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .address(AddressRegisterRequest.builder().build())
                    .build();

            Pacient pacientWithouId = Pacient.builder().build();

            Pacient pacientWithId = Pacient.builder()
                    .id(1L)
                    .build();

            PacientResponse pacientResponse = PacientResponse.builder()
                    .id(1L)
                    .build();

            Log log = Log.builder().build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(false);
            Mockito.when(pacientMapper.map(Mockito.any(PacientRegisterRequest.class)))
                    .thenReturn(pacientWithouId);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                    .thenReturn(pacientWithId);
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(addressService.registerAdress(Mockito.any(Address.class)))
                    .thenReturn(Address.builder().build());


            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);

            PacientResponse result = pacientService.registerPacient(request);

            assertEquals(pacientWithId.getId(), result.getId());
        }

        @Test
        @DisplayName("When pacient has alergies list with two items, alergyService.registerAlergy should be called twice")
        void test3() {
            List<String> alergies = List.of("alergia1", "alergia2");

            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .alergies(alergies)
                    .address(AddressRegisterRequest.builder().build())
                    .build();

            Pacient pacientWithouId = Pacient.builder().build();

            Pacient pacientWithId = Pacient.builder()
                    .id(1L)
                    .build();

            PacientResponse pacientResponse = PacientResponse.builder()
                    .id(1L)
                    .build();

            Log log = Log.builder().build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(false);
            Mockito.when(pacientMapper.map(Mockito.any(PacientRegisterRequest.class)))
                    .thenReturn(pacientWithouId);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                    .thenReturn(pacientWithId);
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(addressService.registerAdress(Mockito.any(Address.class)))
                    .thenReturn(Address.builder().build());
            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
            doNothing().when(alergyService).registerAlergy(Mockito.any(Pacient.class), Mockito.anyString());

            pacientService.registerPacient(request);

            Mockito.verify(alergyService, Mockito.times(2)).registerAlergy(Mockito.any(Pacient.class), Mockito.anyString());
        }

        @Test
        @DisplayName("When pacient has special care list with three items, specialCareService.registerSpecialCare should be called three time")
        void test4(){
            List<String> specialCare = List.of("specialCare1", "specialCare2", "specialCare3");

            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .specialCare(specialCare)
                    .address(AddressRegisterRequest.builder().build())
                    .build();

            Pacient pacientWithouId = Pacient.builder().build();

            Pacient pacientWithId = Pacient.builder()
                    .id(1L)
                    .build();

            PacientResponse pacientResponse = PacientResponse.builder()
                    .id(1L)
                    .build();

            Log log = Log.builder().build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(false);
            Mockito.when(pacientMapper.map(Mockito.any(PacientRegisterRequest.class)))
                    .thenReturn(pacientWithouId);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                    .thenReturn(pacientWithId);
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(addressService.registerAdress(Mockito.any(Address.class)))
                    .thenReturn(Address.builder().build());
            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);
            doNothing().when(specialCareService).registerSpecialCare(Mockito.any(Pacient.class), Mockito.anyString());

            pacientService.registerPacient(request);

            Mockito.verify(specialCareService, Mockito.times(3)).registerSpecialCare(Mockito.any(Pacient.class), Mockito.anyString());
        }

        @Test
        @DisplayName("When pacient has no alergy or special care, specialCareService.registerSpecialCare and alergyService.registerAlergy should not be called")
        void test5(){
            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .address(AddressRegisterRequest.builder().build())
                    .build();

            Pacient pacientWithouId = Pacient.builder().build();

            Pacient pacientWithId = Pacient.builder()
                    .id(1L)
                    .build();

            PacientResponse pacientResponse = PacientResponse.builder()
                    .id(1L)
                    .build();

            Log log = Log.builder().build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(false);
            Mockito.when(pacientMapper.map(Mockito.any(PacientRegisterRequest.class)))
                    .thenReturn(pacientWithouId);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                    .thenReturn(pacientWithId);
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(addressService.registerAdress(Mockito.any(Address.class)))
                    .thenReturn(Address.builder().build());
            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);

            pacientService.registerPacient(request);

            Mockito.verify(specialCareService, Mockito.times(0)).registerSpecialCare(Mockito.any(Pacient.class), Mockito.anyString());
            Mockito.verify(alergyService, Mockito.times(0)).registerAlergy(Mockito.any(Pacient.class), Mockito.anyString());
        }
    }

    @Nested
    @DisplayName("Tests of checkIfPacientExists method")
    class checkIfPacientExistsTest {
        @Test
        @DisplayName("When pacient with same cpf or email is NOT found, it should return false")
        void test1() {
            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .build();

            Boolean result = pacientService.checkIfPacientExists(request);

            assertFalse(result);
        }

        @Test
        @DisplayName("When pacient with same cpf or email is found, it should return true")
        void test2() {
            PacientRegisterRequest request = PacientRegisterRequest.builder()
                    .cpf("1234")
                    .email("email")
                    .build();

            Mockito.when(pacientRepository.existsByEmailOrCpf(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(true);

            Boolean result = pacientService.checkIfPacientExists(request);

            assertTrue(result);
        }
    }

}