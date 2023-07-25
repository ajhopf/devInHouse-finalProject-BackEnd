package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.DietRegisterRequest;
import com.example.labmedical.controller.dtos.response.DietResponse;
import com.example.labmedical.controller.mapper.DietMapper;
import com.example.labmedical.repository.DietRepository;
import com.example.labmedical.repository.model.Diet;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@SpringBootTest
class DietServiceTest {
    @Mock
    private DietRepository dietRepository;
    @Mock
    private PacientService pacientService;
    @Mock
    private DietMapper dietMapper;
    @Mock
    private LogService logService;
    @InjectMocks
    private DietService dietService;

    @Nested
    @DisplayName("Tests of registerDiet method")
    class registerDietTests {
        @Test
        @DisplayName("When no pacient is found with given pacientId, it should throw EntityNotFoundException")
        void test1() {
            DietRegisterRequest request = DietRegisterRequest.builder().pacientId(1L).build();
            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> dietService.registerDiet(request));
        }

        @Test
        @DisplayName("When pacient is found, it should save the diet")
        void test2() {
            DietRegisterRequest request = DietRegisterRequest.builder().pacientId(1L).build();
            Pacient pacient = Pacient.builder().id(1L).build();
            Diet diet = Diet.builder().pacient(pacient).id(1L).build();
            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(pacient);
            Mockito.when(dietMapper.map(Mockito.any(DietRegisterRequest.class)))
                    .thenReturn(diet);
            Mockito.when(dietRepository.save(Mockito.any(Diet.class)))
                            .thenReturn(diet);

            dietService.registerDiet(request);

            Mockito.verify(dietRepository, times(1))
                    .save(diet);
        }
    }

    @Nested
    @DisplayName("Tests of deleteDiet method")
    class deleteDietTests{
        @Test
        @DisplayName("When no diet is found with given id, it should throw entity not found exception")
        void test1() {
            assertThrows(EntityNotFoundException.class, () -> dietService.deleteDiet(1L));
        }

        @Test
        @DisplayName("When diet is found with given id, it should delete it")
        void test2() {
            Mockito.when(dietRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(Diet.builder().build()));
            dietService.deleteDiet(1L);

            Mockito.verify(dietRepository, times(1))
                    .deleteById(1L);
        }
    }

    @Nested
    @DisplayName("Tests of getDiets method")
    class getDietsTests {
        @Test
        @DisplayName("When no diet is found with given pacient name, it should return empty list")
        void test1() {
            String pacientName = "Andre Hopf";
            List<Diet> emptyDietList = new ArrayList<>();
            List<DietResponse> emptyDietResponseList = new ArrayList<>();

            Mockito.when(dietRepository.findAllByPacient_NameContaining(Mockito.anyString()))
                    .thenReturn(emptyDietList);
            Mockito.when(dietMapper.map(emptyDietList))
                    .thenReturn(emptyDietResponseList);

            List<DietResponse> result = dietService.getDiets(pacientName);

            Mockito.verify(dietRepository, times(0))
                    .findAll();
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("When pacientName is given, it should return list with diets with that given pacientName")
        void test2() {
            String pacientName = "Andre Hopf";
            List<Diet> dietList = List.of(
                    Diet.builder().build(),
                    Diet.builder().build()
            );

            List<DietResponse> dietResponseList = List.of(
                    DietResponse.builder().build(),
                    DietResponse.builder().build()
            );

            Mockito.when(dietRepository.findAllByPacient_NameContaining(Mockito.anyString()))
                    .thenReturn(dietList);
            Mockito.when(dietMapper.map(dietList))
                    .thenReturn(dietResponseList);

            List<DietResponse> result = dietService.getDiets(pacientName);

            Mockito.verify(dietRepository, times(0))
                    .findAll();
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("When no pacientName is given, it should return list with all diets")
        void test3() {
            List<Diet> dietList = List.of(
                    Diet.builder().build(),
                    Diet.builder().build()
            );

            List<DietResponse> dietResponseList = List.of(
                    DietResponse.builder().build(),
                    DietResponse.builder().build()
            );

            Mockito.when(dietRepository.findAll())
                    .thenReturn(dietList);
            Mockito.when(dietMapper.map(dietList))
                    .thenReturn(dietResponseList);

            List<DietResponse> result = dietService.getDiets(null);

            Mockito.verify(dietRepository, times(0))
                    .findAllByPacient_NameContaining(Mockito.anyString());
            assertEquals(2, result.size());
        }
    }

}