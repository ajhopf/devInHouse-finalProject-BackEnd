package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.ExerciseRequest;
import com.example.labmedical.controller.dtos.response.ExerciseResponse;
import com.example.labmedical.repository.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ExerciseMapperComponent.class})
public interface ExerciseMapper {
    @Mapping(source = "patientId", target = "patient")
    Exercise map(ExerciseRequest exerciseRequest);
    @Mapping(source = "patient.id", target = "patientId")
    ExerciseResponse map(Exercise exercise);
}
