package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.LogRequest;
import com.example.labmedical.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logger;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> create(
            @RequestBody LogRequest logRequest) {
            logger.logFromFront(logRequest);
            return ResponseEntity.ok().build();
    }
}
