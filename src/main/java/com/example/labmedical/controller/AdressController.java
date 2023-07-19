package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.AddressRegisterRequest;
import com.example.labmedical.controller.dtos.response.AddressResponse;
import com.example.labmedical.repository.model.Address;
import com.example.labmedical.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/enderecos")
public class AdressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Address> registerAddress(
            @RequestBody @Valid AddressRegisterRequest addressRegisterRequest,
            UriComponentsBuilder uriBuilder
            ) {
        Address address = addressService.registerAdress(addressRegisterRequest);

        URI uri = uriBuilder.path("/api/enderecos/{id}")
                .buildAndExpand(address.getId())
                .toUri();


        return ResponseEntity.created(uri).body(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse address = addressService.getAddressById(id);

        return ResponseEntity.ok(address);
    }
}
