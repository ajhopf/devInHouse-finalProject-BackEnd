package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ExamRequest;
import com.example.labmedical.controller.dtos.request.ExamUpdate;
import com.example.labmedical.controller.dtos.response.ExamResponse;
import com.example.labmedical.controller.mapper.ExamMapper;
import com.example.labmedical.repository.ExamRepository;
import com.example.labmedical.repository.model.Appointment;
import com.example.labmedical.repository.model.Exam;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ExamResponse updateExam(Long examId, ExamUpdate newExam) {
       boolean examExist = examRepository.existsById(examId);

       if(!examExist) {
           throw new EntityNotFoundException(String.format("Exame id: %d não encontrado",examId));
       }
       Exam currentExam = examRepository.findById(examId).get();
       Exam examWithNewInfo = examMapper.map(newExam);
       examWithNewInfo.setId(examId);
       examWithNewInfo.setPacient(currentExam.getPacient());

       examRepository.save(examWithNewInfo);
       logService.success(String.format("O exame id: %d foi atualiza", examId));
        ExamResponse response = examMapper.map(examWithNewInfo);
        return response;
     }
  
    public List<ExamResponse> getExams(Long pacientId) {
        List<Exam> examList;

        if (pacientId != null) {
            pacientService.getPacientById(pacientId);

            examList = examRepository.getExamsByPacient_Id(pacientId);
            logService.success("Busca de lista de exames do paciente " + pacientId + " realizada.");
        } else {
            examList = examRepository.findAll();
            logService.success("Busca de lista de exames de todos pacientes realizada.");
        }

        return examMapper.map(examList);
    }
}
