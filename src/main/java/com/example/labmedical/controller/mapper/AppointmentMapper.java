package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.repository.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {
    @Mapping(target = "pacient.id", source = "pacientId")
    @Mapping(target = "medicine.id", source = "medicineId")
    Appointment map(AppointmentRegisterRequest source);
    @Mapping(target = "pacientId", source = "pacient.id")
    @Mapping(target = "medicineId", source = "medicine.id")
    AppointmentResponse map(Appointment source);

    @Mapping(target = "pacientId", source = "pacient.id")
    @Mapping(target = "medicineId", source = "medicine.id")
    List<AppointmentResponse> map(List<Appointment> source);
}
