package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.PacientRegisterRequest;
import com.example.labmedical.controller.dtos.response.PacientResponse;
import com.example.labmedical.repository.model.Pacient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PacientMapper {
//    @Mappings({
//            @Mapping(source = "dob", target = "dob", dateFormat = "dd/mm/yyyy"),
//            @Mapping(source = "healthInsuranceExpirationDate", target = "healthInsuranceExpirationDate", dateFormat = "dd/mm/yyyy")
//    })
    Pacient map(PacientRegisterRequest source);

    PacientResponse map(Pacient source);
}
