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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/exames")
@RequiredArgsConstructor
public class ExamController {
    @Autowired private ExamService examService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PutMapping("atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long id,
            @RequestBody ExamUpdate request
    ){
        ExamResponse exam = examService.updateExam(id, request);
        return ResponseEntity.ok(exam);
    }
  
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExamResponse>> getExams(
            @RequestParam(required = false) Long pacientId,
            @RequestParam(required = false) String pacientName
    ) {
        List<ExamResponse> examList = examService.getExams(pacientId, pacientName);

        return ResponseEntity.ok(examList);
    }

}
