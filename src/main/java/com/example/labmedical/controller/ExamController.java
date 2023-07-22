package com.example.labmedical.controller;

import com.example.labmedical.repository.ExamRepository;
import com.example.labmedical.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exames")
@RequiredArgsConstructor
public class ExamController {
    @Autowired private ExamService examService;

}
