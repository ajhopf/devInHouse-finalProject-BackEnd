package com.example.labmedical.service;

import com.example.labmedical.repository.SpecialCareRepository;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.repository.model.Pacient;
import com.example.labmedical.repository.model.SpecialCare;
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
class SpecialCareServiceTest {
    @Mock
    private SpecialCareRepository specialCareRepository;
    @Mock
    private LogService logService;
    @InjectMocks
    private SpecialCareService specialCareService;

    @Nested
    @DisplayName("Tests of registerSpecialCare method")
    class registerSpecialCareTests {
        @Test
        @DisplayName("When registering special care, specialCareRepository.save should be called once")
        void test1() {
            Pacient pacient = Pacient.builder().id(1L).build();
            String specialCareDescription = "special care";

            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(Log.builder().build());

            specialCareService.registerSpecialCare(pacient, specialCareDescription);

            Mockito.verify(specialCareRepository, times(1))
                    .save(Mockito.any(SpecialCare.class));
        }
    }

    @Nested
    @DisplayName("Tests of deleteAllPacientSpecialCares method")
    class deleteAllPacientSpecialCaresTests {
        @Test
        @DisplayName("When pacient has two special cares, should call deleteAll one time with correct special care list")
        void test1() {
            List<SpecialCare> pacientSpecialCares = List.of(
                    SpecialCare.builder().build(),
                    SpecialCare.builder().build()
            );

            Mockito.when(specialCareRepository.getSpecialCaresByPacient_Id(Mockito.anyLong()))
                    .thenReturn(pacientSpecialCares);

            specialCareService.deleteAllPacientSpecialCares(Mockito.anyLong());

            Mockito.verify(specialCareRepository, times(1))
                    .deleteAll(pacientSpecialCares);
        }
    }

    @Nested
    @DisplayName("Tests of getAllPacientSpecialCares method")
    class getAllPacientSpecialCaresTests {
        @Test
        @DisplayName("When pacient has three special cares, it should return List with three items")
        void test1() {
            List<SpecialCare> pacientSpecialCares = List.of(
                    SpecialCare.builder().build(),
                    SpecialCare.builder().build(),
                    SpecialCare.builder().build()
            );

            Mockito.when(specialCareRepository.getSpecialCaresByPacient_Id(Mockito.anyLong()))
                    .thenReturn(pacientSpecialCares);

            List<String> result = specialCareService.getAllPacientSpecialCares(Mockito.anyLong());

            assertEquals(3, result.size());
        }

        @Test
        @DisplayName("When pacient has no alergy, it should return empty list")
        void test2() {
            List<SpecialCare> emptyList = new ArrayList<>();

            Mockito.when(specialCareRepository.getSpecialCaresByPacient_Id(Mockito.anyLong()))
                    .thenReturn(emptyList);

            List<String> result = specialCareService.getAllPacientSpecialCares(Mockito.anyLong());

            assertEquals(0, result.size());
        }
    }

}