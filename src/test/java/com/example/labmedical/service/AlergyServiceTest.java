package com.example.labmedical.service;

import com.example.labmedical.repository.AlergyRepository;
import com.example.labmedical.repository.model.Alergy;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.Pacient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@SpringBootTest
class AlergyServiceTest {
    @Mock
    private AlergyRepository alergyRepository;
    @Mock
    private LogService logService;
    @InjectMocks
    private AlergyService alergyService;

    @Nested
    @DisplayName("Tests of registerAlergy method")
    class registerAlergyTests {
        @Test
        @DisplayName("When registering alergy, alergyRepository.save should be called once")
        void test1() {
            Pacient pacient = Pacient.builder().id(1L).build();
            String alergyDescription = "alergy";

            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(Log.builder().build());

            alergyService.registerAlergy(pacient, alergyDescription);

            Mockito.verify(alergyRepository, times(1))
                    .save(Mockito.any(Alergy.class));
        }
    }

    @Nested
    @DisplayName("Tests of deleteAllPacientAlergies method")
    class deleteAllPacientAlergiesTests {
        @Test
        @DisplayName("When pacient has two alergies, should call deleteAll one time with correct alergy list")
        void test1() {
            List<Alergy> pacientAlergies = List.of(
                    Alergy.builder().build(),
                    Alergy.builder().build()
            );

            Mockito.when(alergyRepository.getAlergiesByPacient_Id(Mockito.anyLong()))
                    .thenReturn(pacientAlergies);

            alergyService.deleteAllPacientAlergies(Mockito.anyLong());

            Mockito.verify(alergyRepository, times(1))
                    .deleteAll(pacientAlergies);
        }
    }


    @Nested
    @DisplayName("Tests of getAllPacientAlergies method")
    class getAllPacientAlergiesTests {
        @Test
        @DisplayName("When pacient has two alergies, it should return List with two items")
        void test1() {
            List<Alergy> pacientAlergies = List.of(
                    Alergy.builder().build(),
                    Alergy.builder().build()
            );

            Mockito.when(alergyRepository.getAlergiesByPacient_Id(Mockito.anyLong()))
                    .thenReturn(pacientAlergies);

            List<String> result = alergyService.getAllPacientAlergies(Mockito.anyLong());

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When pacient has no alergy, it should return empty list")
        void test2() {
            List<Alergy> emptyAlergyList = new ArrayList<>();

            Mockito.when(alergyRepository.getAlergiesByPacient_Id(Mockito.anyLong()))
                    .thenReturn(emptyAlergyList);

            List<String> result = alergyService.getAllPacientAlergies(Mockito.anyLong());

            assertEquals(0, result.size());
        }
    }

}