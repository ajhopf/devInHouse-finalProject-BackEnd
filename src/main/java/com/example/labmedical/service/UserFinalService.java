package com.example.labmedical.service;

import com.example.labmedical.repository.UserFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFinalService {
    @Autowired
    private UserFinalRepository userFinalRepository;
}
