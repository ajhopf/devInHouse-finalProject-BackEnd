package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.MedicineRegisterRequest;
import com.example.labmedical.controller.dtos.response.MedicineResponse;
import com.example.labmedical.repository.model.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineMapper {

    @Mapping(target = "pacient.id", source = "pacientId")
    Medicine map(MedicineRegisterRequest source);

    @Mapping(target = "pacientId", source = "pacient.id")
    MedicineResponse map(Medicine source);

}
