package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.controller.mapper.ExamMapper;
import com.example.labmedical.repository.ExamRepository;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Exam;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private LogService logService;

    public ExamResponse registerExam(ExamRequest request) {
        pacientService.getPacientById(request.getPacientId());

        Exam exam = examMapper.map(request);
        examRepository.save(exam);
        logService.success(String.format("Exame registrado. Id exame: %d; Id Paciente: %d", exam.getId(), exam.getPacient().getId()));
        return examMapper.map(exam);
    }

    public void deleteExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exame com id %d não encontrado.", examId)));
        logService.success(String.format("Exame id: %d removido", examId));
        examRepository.delete(exam);
    }
}
