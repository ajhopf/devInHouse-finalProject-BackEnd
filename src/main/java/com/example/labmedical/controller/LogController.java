package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.request.LogRequest;
import com.example.labmedical.controller.dtos.response.LogResponse;
import com.example.labmedical.controller.mapper.LogMapper;
import com.example.labmedical.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logger;

    @Autowired
    private LogMapper logMapper;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> create(
            @RequestBody LogRequest logRequest) {
            logger.logFromFront(logRequest);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<LogResponse>> getAll(
            @RequestHeader(value = "page", defaultValue = "1" ) int pageNumber,
            @RequestHeader(value = "page-size", defaultValue = "10") int pageSize) {
//        for (int i = 0; i < 100; i++) {
//            logger.success("Descricao Log" + i);
//        }
        var logPage = logger.getAll(pageNumber, pageSize);
        var logResponsePage = logPage.map( log -> logMapper.map(log));
        return ResponseEntity.ok().body(logResponsePage);
    }
}
