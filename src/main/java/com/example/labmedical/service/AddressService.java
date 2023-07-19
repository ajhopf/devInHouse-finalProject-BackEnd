package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.response.AddressResponse;
import com.example.labmedical.controller.mapper.AdressMapper;
import com.example.labmedical.repository.AddressRepository;
import com.example.labmedical.repository.model.Address;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AdressMapper adressMapper;
    @Autowired
    private LogService logService;

    public AddressResponse registerAdress(AddressRegisterRequest adressPostRequest) {
        Address address = adressMapper.map(adressPostRequest);

        address = addressRepository.save(address);

        String logDescription = "O endereço com id " + address.getId() + " foi registrado.";
        logService.success(logDescription);

        return adressMapper.map(address);
    }

    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço com id " + id + " não encontrado."));

        return adressMapper.map(address);
    }
}
