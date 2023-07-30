package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.MedicineRegisterRequest;
import com.example.labmedical.controller.dtos.response.MedicineResponse;
import com.example.labmedical.controller.mapper.MedicineMapper;
import com.example.labmedical.repository.MedicineRepository;
import com.example.labmedical.repository.model.Medicine;
import com.example.labmedical.repository.model.Pacient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private MedicineMapper medicineMapper;
    @Mock
    private PacientService pacientService;
    @Mock
    private LogService logService;
    @InjectMocks
    private MedicineService medicineService;

    @Nested
    @DisplayName("Tests of registerMedicine method")
    class registerMedicineTests {
        @Test
        @DisplayName("When registering medicine and pacientId is not found, it should throw EntityNotFoundException")
        void test1() {
            MedicineRegisterRequest request = MedicineRegisterRequest.builder().pacientId(1L).build();

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> medicineService.registerMedicine(request));
        }

        @Test
        @DisplayName("When registering medicine and pacientId is found, it should save medicine")
        void test2() {
            MedicineRegisterRequest request = MedicineRegisterRequest.builder().pacientId(1L).build();
            Pacient pacient = Pacient.builder().id(1L).build();
            Medicine medicine = Medicine.builder().id(1L).pacient(pacient).build();
            MedicineResponse medicineResponse = MedicineResponse.builder().build();

            Mockito.when(pacientService.getPacientById(Mockito.anyLong()))
                    .thenReturn(pacient);
            Mockito.when(medicineMapper.map(Mockito.any(MedicineRegisterRequest.class)))
                    .thenReturn(medicine);
            Mockito.when(medicineMapper.map(Mockito.any(Medicine.class)))
                    .thenReturn(medicineResponse);

            medicineService.registerMedicine(request);

            Mockito.verify(medicineRepository, times(1))
                    .save(medicine);
        }
    }
}