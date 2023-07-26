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

    public Address registerAdress(AddressRegisterRequest adressPostRequest) {
        Address address = adressMapper.map(adressPostRequest);

        address = addressRepository.save(address);

        String logDescription = "O endereço com id " + address.getId() + " foi registrado.";
        logService.success(logDescription);

        return address;
    }

    public Address registerAdress(Address address) {
        Address savedAddress = addressRepository.save(address);

        String logDescription = "O endereço com id " + savedAddress.getId() + " foi registrado.";
        logService.success(logDescription);

        return savedAddress;
    }

    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço com id " + id + " não encontrado."));

        return adressMapper.map(address);
    }

    public Address updateAddressById(Long addressId, AddressRegisterRequest newAddressInfo) {
        boolean addressExists = addressRepository.existsById(addressId);

        if (!addressExists) {
            throw new EntityNotFoundException("Endereço não encontrado");
        }

        Address addressWithNewInfo = adressMapper.map(newAddressInfo);
        addressWithNewInfo.setId(addressId);

        addressRepository.save(addressWithNewInfo);

        logService.success("O endereço com id " + addressId + " foi atualizado");

        return addressWithNewInfo;
    }

    public void deleteAddress(Address address) {
        boolean addressExists = addressRepository.existsById(address.getId());

        if (!addressExists) {
            throw new EntityNotFoundException("Endereço não encontrado");
        }

        addressRepository.delete(address);
    }
}
