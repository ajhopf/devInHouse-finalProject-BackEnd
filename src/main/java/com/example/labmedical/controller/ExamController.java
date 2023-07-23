package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.AppointmentRegisterRequest;
import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.request.ExamUpdate;
import com.example.labmedical.controller.dtos.response.AppointmentResponse;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/exames")
@RequiredArgsConstructor
public class ExamController {
    @Autowired private ExamService examService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ExamResponse> registerExam(
            @RequestBody @Valid ExamRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ExamResponse exam = examService.registerExam(request);

        URI uri = uriBuilder.path("/api/consultas/{id}")
                .buildAndExpand(exam.getId())
                .toUri();

        return ResponseEntity.created(uri).body(exam);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long id,
            @RequestBody ExamUpdate request
    ){
        ExamResponse exam = examService.updateExam(id, request);
        return ResponseEntity.ok(exam);
    }

}
