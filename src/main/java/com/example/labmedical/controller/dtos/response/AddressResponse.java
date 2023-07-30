package com.example.labmedical.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String cep;
    private String city;
    private String state;
    private String street;
    private String houseNumber;
    private String complement;
    private String district;
    private String referencePoint;
}
