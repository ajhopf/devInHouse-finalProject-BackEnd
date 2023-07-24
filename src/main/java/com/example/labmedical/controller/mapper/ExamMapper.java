package com.example.labmedical.controller.mapper;

import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.request.ExamUpdate;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ExamMapper {
    @Mapping(target = "pacient.id", source = "pacientId")
    Exam map(ExamRequest source);

    Exam map(ExamUpdate source);

    @Mapping(target = "pacientId", source = "pacient.id")
    ExamResponse map(Exam source);

    @Mapping(target = "pacientId", source = "pacient.id")
    List<ExamResponse> map(List<Exam> source);


}
