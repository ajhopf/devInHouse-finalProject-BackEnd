package com.example.labmedical.service;

import com.example.labmedical.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private LogService logService;
}
