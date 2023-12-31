package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.response.AddressResponse;
import com.example.labmedical.controller.mapper.AdressMapper;
import com.example.labmedical.repository.AddressRepository;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.repository.model.Log;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private LogService logService;
    @Mock
    private AdressMapper adressMapper;
    @InjectMocks
    private AddressService addressService;

    @Nested
    @DisplayName("Tests of registerAddress method")
    class registerAddressTests {
        @Test
        @DisplayName("When registering an AddressRegisterRequest, it should return an address with id")
        void test1() {
            Address address = Address.builder().build();
            Address addressWithId = Address.builder()
                    .id(1L)
                    .build();
            AddressRegisterRequest addressRegisterRequest = AddressRegisterRequest.builder().build();
            AddressResponse addressResponse = AddressResponse.builder().id(1L).build();
            Log log = Log.builder().build();

            Mockito.when(adressMapper.map(Mockito.any(AddressRegisterRequest.class)))
                    .thenReturn(address);
            Mockito.when(adressMapper.map(Mockito.any(Address.class)))
                    .thenReturn(addressResponse);
            Mockito.when(addressRepository.save(Mockito.any(Address.class)))
                    .thenReturn(addressWithId);
            Mockito.when(logService.success(Mockito.anyString())).thenReturn(log);

            Address result = addressService.registerAdress(addressRegisterRequest);

            assertEquals(addressWithId.getId(), result.getId());
        }

        @Test
        @DisplayName("When registering an Address, it should return an address with id")
        void test2() {
            Address address = Address.builder().build();
            Address addressWithId = Address.builder().id(1L).build();

            Mockito.when(addressRepository.save(Mockito.any(Address.class)))
                    .thenReturn(addressWithId);
            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(Log.builder().build());

            Address result = addressService.registerAdress(address);

            assertEquals(addressWithId.getId(), result.getId());
        }
    }

    @Nested
    @DisplayName("Tests of getAddressById method")
    class getAddressByIdTests {
        @Test
        @DisplayName("When no address is found with given id, it should throw EntityNotFoundException")
        void test1() {
            assertThrows(EntityNotFoundException.class, () -> addressService.getAddressById(Mockito.anyLong()));
        }

        @Test
        @DisplayName("When address is found, it should return AddressResponse")
        void test2() {
            Address address = Address.builder().build();

            AddressResponse addressResponse = AddressResponse.builder()
                    .id(1L)
                    .cep("12345689")
                    .build();

            Mockito.when(addressRepository.findById(Mockito.anyLong()))
                    .thenReturn(Optional.of(address));
            Mockito.when(adressMapper.map(Mockito.any(Address.class)))
                    .thenReturn(addressResponse);

            AddressResponse response = addressService.getAddressById(Mockito.anyLong());

            assertEquals(addressResponse.getId(), response.getId());

        }
    }

    @Nested
    @DisplayName("Tests of updateAddressById method")
    class updateAddressByIdTest {
        @Test
        @DisplayName("When address doesnt exist, it should throw EntityNotFoundException")
        void test1() {
            assertThrows(EntityNotFoundException.class,
                    () -> addressService.updateAddressById(1L, AddressRegisterRequest.builder().build()));
        }

        @Test
        @DisplayName("When address is found, it should return Address object with the correct id and infos")
        void test2() {
            Address address = Address
                    .builder()
                    .city("Florianopolis")
                    .state("SC")
                    .build();

            Log log = Log.builder().build();

            Mockito.when(addressRepository.existsById(Mockito.anyLong())).thenReturn(true);
            Mockito.when(adressMapper.map(Mockito.any(AddressRegisterRequest.class)))
                    .thenReturn(address);
            Mockito.when(addressRepository.save(Mockito.any(Address.class)))
                    .thenReturn(address);
            Mockito.when(logService.success(Mockito.anyString()))
                    .thenReturn(log);

            Address result = addressService.updateAddressById(1L, AddressRegisterRequest.builder().build());

            assertEquals(1L, result.getId());
            assertEquals("Florianopolis", result.getCity());
            assertEquals("SC", result.getState());
        }
    }

    @Nested
    @DisplayName("Tests of deleteAddress method")
    class deleteAddressTests{
        @Test
        @DisplayName("When address doesnt exist, it should throw EntityNotFoundException")
        void test1() {
            Address address = Address.builder().id(1L).build();

            assertThrows(EntityNotFoundException.class, () -> addressService.deleteAddress(address));
        }

        @Test
        @DisplayName("When address is found, it should delete it")
        void test2() {
            Address address = Address.builder().id(1L).build();
            Mockito.when(addressRepository.existsById(Mockito.anyLong()))
                    .thenReturn(true);

            addressService.deleteAddress(address);

            Mockito.verify(addressRepository, Mockito.times(1))
                    .delete(address);
        }
    }


}