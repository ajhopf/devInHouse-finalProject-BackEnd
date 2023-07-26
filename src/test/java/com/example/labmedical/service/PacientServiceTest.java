package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.request.PacientUpdateRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.controller.mapper.PacientMapper;
import com.example.labmedical.exceptions.PacientAlreadyRegisteredException;
import com.example.labmedical.exceptions.PatientWithRecordsException;
import com.example.labmedical.repository.*;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.Pacient;
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
import static org.mockito.Mockito.times;

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
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ExamRepository examRepository;
    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private DietRepository dietRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @InjectMocks
    private PacientService pacientService;

    @Nested
    @DisplayName("Tests of getPacients method")
    class getPacientsTest {
        @Test
        @DisplayName("When two pacients are registered, it should return List with two PacientResponse")
        void test1() {
            List<Pacient> pacientList = List.of(
                    Pacient.builder().build(),
                    Pacient.builder().build()
            );

            Mockito.when(pacientRepository.findAll()).thenReturn(pacientList);
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class))).thenReturn(PacientResponse.builder().build());

            List<PacientResponse> result = pacientService.getPacients();

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When no pacient is registered, it should return empty list")
        void test2() {
            List<PacientResponse> result = pacientService.getPacients();
            assertEquals(0, result.size());
        }
    }

    @Nested
    @DisplayName("Tests of getPacientById method")
    class getPacientByIdTest {
        @Test
        @DisplayName("When pacient is found with given id, it should return the patient")
        void test1() {
            PacientResponse pacientResponse = PacientResponse.builder().id(1L).name("Andre").build();

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Pacient.builder().build()));
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(alergyService.getAllPacientAlergies(Mockito.anyLong()))
                    .thenReturn(new ArrayList<>());
            Mockito.when(specialCareService.getAllPacientSpecialCares(Mockito.anyLong()))
                    .thenReturn(new ArrayList<>());

            PacientResponse result = pacientService.getPacientResponseById(1L);

            assertEquals("Andre", result.getName());
            assertEquals(1L, result.getId());
        }

        @Test
        @DisplayName("When pacient is found with given id and has alergies, it should return the patient infos with alergies list")
        void test2() {
            PacientResponse pacientResponse = PacientResponse.builder().id(1L).name("Andre").build();
            List<String> pacientAlergies = List.of("alergia", "outra alergia");

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Pacient.builder().build()));
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(alergyService.getAllPacientAlergies(Mockito.anyLong()))
                    .thenReturn(pacientAlergies);
            Mockito.when(specialCareService.getAllPacientSpecialCares(Mockito.anyLong()))
                    .thenReturn(new ArrayList<>());

            PacientResponse result = pacientService.getPacientResponseById(1L);

            assertEquals(pacientAlergies, result.getAlergies());
        }

        @Test
        @DisplayName("When pacient is found with given id and has special care, it should return the patient infos with special care list")
        void test3() {
            PacientResponse pacientResponse = PacientResponse.builder().id(1L).name("Andre").build();
            List<String> pacientSpecialCares = List.of("cuidado especial");

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Pacient.builder().build()));
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(alergyService.getAllPacientAlergies(Mockito.anyLong()))
                    .thenReturn(new ArrayList<>());
            Mockito.when(specialCareService.getAllPacientSpecialCares(Mockito.anyLong()))
                    .thenReturn(pacientSpecialCares);

            PacientResponse result = pacientService.getPacientResponseById(1L);

            assertEquals(pacientSpecialCares, result.getSpecialCare());
        }

        @Test
        @DisplayName("When no pacient is found with given id, it should throw EntityNotFoundException")
        void test4() {
            assertThrows(EntityNotFoundException.class, () -> pacientService.getPacientResponseById(Mockito.anyLong()));
        }
    }

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

    @Nested
    @DisplayName("Tests of updatePacient method")
    class updatePacientTests {
        @Test
        @DisplayName("When pacient is not found, it should throw EntityNotFoundException")
        void test1() {
            assertThrows(EntityNotFoundException.class,
                    () ->  pacientService.updatePacient(PacientUpdateRequest.builder().build(), Mockito.anyLong()));
        }

        @Test
        @DisplayName("When request has alergies, it should delete all pacient alergies and save new alergies")
        void test2() {
            Address address = Address.builder().id(1L).build();
            Pacient pacient = Pacient.builder()
                    .cpf("12345")
                    .rg("9090")
                    .address(address)
                    .build();
            PacientResponse pacientResponse = PacientResponse.builder().build();
            PacientUpdateRequest pacientUpdateRequest = PacientUpdateRequest.builder().build();
            pacientUpdateRequest.setAlergies(List.of("alergy1", "alergy2"));

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(pacient));
            doNothing().when(alergyService).registerAlergy(Mockito.any(Pacient.class), Mockito.anyString());
            Mockito.when(addressService.updateAddressById(Mockito.anyLong(), Mockito.any(AddressRegisterRequest.class)))
                    .thenReturn(address);
            Mockito.when(pacientMapper.map(Mockito.any(PacientUpdateRequest.class)))
                    .thenReturn(pacient);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                            .thenReturn(Pacient.builder().build());
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                            .thenReturn(pacientResponse);
            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(Log.builder().build());

            pacientService.updatePacient(pacientUpdateRequest, 1L);

            Mockito.verify(alergyService, times(1))
                    .deleteAllPacientAlergies(Mockito.anyLong());
            Mockito.verify(alergyService, times(2))
                    .registerAlergy(Mockito.any(Pacient.class), Mockito.anyString());
        }

        @Test
        @DisplayName("When request has specialCares, it should delete all pacient specialCares and save new special cares")
        void test3() {
            Address address = Address.builder().id(1L).build();
            Pacient pacient = Pacient.builder()
                    .cpf("12345")
                    .rg("9090")
                    .address(address)
                    .build();
            PacientResponse pacientResponse = PacientResponse.builder().build();
            PacientUpdateRequest pacientUpdateRequest = PacientUpdateRequest.builder().build();
            pacientUpdateRequest.setSpecialCare(List.of("special", "care", "one more"));

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(pacient));
            doNothing().when(specialCareService).registerSpecialCare(Mockito.any(Pacient.class), Mockito.anyString());
            Mockito.when(addressService.updateAddressById(Mockito.anyLong(), Mockito.any(AddressRegisterRequest.class)))
                    .thenReturn(address);
            Mockito.when(pacientMapper.map(Mockito.any(PacientUpdateRequest.class)))
                    .thenReturn(pacient);
            Mockito.when(pacientRepository.save(Mockito.any(Pacient.class)))
                    .thenReturn(Pacient.builder().build());
            Mockito.when(pacientMapper.map(Mockito.any(Pacient.class)))
                    .thenReturn(pacientResponse);
            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(Log.builder().build());

            pacientService.updatePacient(pacientUpdateRequest, 1L);

            Mockito.verify(specialCareService, times(1))
                    .deleteAllPacientSpecialCares(Mockito.anyLong());
            Mockito.verify(specialCareService, times(3))
                    .registerSpecialCare(Mockito.any(Pacient.class), Mockito.anyString());
        }
    }

    @Nested
    @DisplayName("Tests of hasRecords method")
    class hasRecordsTests {
        @Test
        @DisplayName("When patient has appointment registered, it should return true")
        void test1() {
            Mockito.when(appointmentRepository.existsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(true);

            assertTrue(pacientService.hasRecords(1L));
        }

        @Test
        @DisplayName("When patient has exam registered, it should return true")
        void test2() {
            Mockito.when(examRepository.existsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(true);

            assertTrue(pacientService.hasRecords(1L));
        }
        @Test
        @DisplayName("When patient has medicine registered, it should return true")
        void test3() {
            Mockito.when(medicineRepository.existsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(true);

            assertTrue(pacientService.hasRecords(1L));
        }
        @Test
        @DisplayName("When patient has diet registered, it should return true")
        void test4() {
            Mockito.when(dietRepository.existsByPacient_Id(Mockito.anyLong()))
                    .thenReturn(true);

            assertTrue(pacientService.hasRecords(1L));
        }

        @Test
        @DisplayName("When patient has exercise registered, it should return true")
        void test5() {
            Mockito.when(exerciseRepository.existsByPatient_Id(Mockito.anyLong()))
                    .thenReturn(true);

            assertTrue(pacientService.hasRecords(1L));
        }

        @Test
        @DisplayName("When patient has no records registeres, it should return false")
        void test6() {
            assertFalse(pacientService.hasRecords(1L));
        }
    }

    @Nested
    @DisplayName("Tests of deletePacient method")
    class deletePacientTests{
        @Test
        @DisplayName("When no patient is found with given id, it should throw EntityNotFoundException")
        void test1() {
            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> pacientService.deletePacient(1L));
        }

        @Test
        @DisplayName("When patient has records, it should throw PatientWithRecordsException")
        void test2() {
            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Pacient.builder().build()));
            Mockito.when(appointmentRepository.existsByPacient_Id(Mockito.anyLong())).thenReturn(true);

            assertThrows(PatientWithRecordsException.class, () -> pacientService.deletePacient(1L));
        }

        @Test
        @DisplayName("When patient doesn't have records, it should delete its alergies and special cares")
        void test3() {
            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Pacient.builder().id(1L).build()));
            Mockito.doNothing().when(alergyService).deleteAllPacientAlergies(Mockito.anyLong());
            Mockito.doNothing().when(specialCareService).deleteAllPacientSpecialCares(Mockito.anyLong());
            Mockito.doNothing().when(pacientRepository).delete(Mockito.any(Pacient.class));
            Mockito.doNothing().when(addressService).deleteAddress(Mockito.any(Address.class));

            pacientService.deletePacient(1L);

            Mockito.verify(specialCareService, times(1))
                    .deleteAllPacientSpecialCares(Mockito.anyLong());
            Mockito.verify(alergyService, times(1))
                    .deleteAllPacientAlergies(Mockito.anyLong());
        }

        @Test
        @DisplayName("When patient doesn't have records, it should delete the patient")
        void test4() {
            Pacient pacient = Pacient.builder().id(1L).build();

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(pacient));
            Mockito.doNothing().when(alergyService).deleteAllPacientAlergies(Mockito.anyLong());
            Mockito.doNothing().when(specialCareService).deleteAllPacientSpecialCares(Mockito.anyLong());
            Mockito.doNothing().when(pacientRepository).delete(Mockito.any(Pacient.class));
            Mockito.doNothing().when(addressService).deleteAddress(Mockito.any(Address.class));

            pacientService.deletePacient(1L);

            Mockito.verify(pacientRepository, times(1))
                    .delete(pacient);
        }

        @Test
        @DisplayName("When patient doesn't have records, it should delete the patient's address")
        void test5() {
            Address address = Address.builder().build();
            Pacient pacient = Pacient.builder().id(1L).address(address).build();

            Mockito.when(pacientRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(pacient));
            Mockito.doNothing().when(alergyService).deleteAllPacientAlergies(Mockito.anyLong());
            Mockito.doNothing().when(specialCareService).deleteAllPacientSpecialCares(Mockito.anyLong());
            Mockito.doNothing().when(pacientRepository).delete(Mockito.any(Pacient.class));
            Mockito.doNothing().when(addressService).deleteAddress(Mockito.any(Address.class));

            pacientService.deletePacient(1L);

            Mockito.verify(addressService, times(1))
                    .deleteAddress(address);
        }
    }

}