package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.LogRequest;
import com.example.labmedical.repository.LogRepository;
import com.example.labmedical.repository.model.Log;
import com.example.labmedical.utils.PrintLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    private Log salva(Log log) {
        var logTemp = logRepository.save(log);
        switch (logTemp.getLogType()) {
            case ERROR: {
                PrintLogUtil.error(logTemp);
                break;
            }
            case SUCCESS: {
                PrintLogUtil.success(logTemp);
                break;
            }
            default:
                PrintLogUtil.info(logTemp);
                break;
        }
        return logTemp;
    }

    public Log success(String description) {
        return salva(
            Log.builder()
                    .logType(Log.LogType.SUCCESS)
                    .logOrigin(Log.LogOrigin.BACK_END)
                    .createdDate(new Date())
                    .description(description)
                    .build()
        );
    }

    public Log info(String description) {
        return salva(
                Log.builder()
                        .logType(Log.LogType.INFO)
                        .logOrigin(Log.LogOrigin.BACK_END)
                        .createdDate(new Date())
                        .description(description)
                        .build()
        );
    }

    public Log error(String description) {
        return salva(
                Log.builder()
                        .logType(Log.LogType.ERROR)
                        .logOrigin(Log.LogOrigin.BACK_END)
                        .createdDate(new Date())
                        .description(description)
                        .build()
        );
    }

    public Log logFromFront(LogRequest log) {
        return salva(
                Log.builder()
                        .logType(log.getLogType())
                        .logOrigin(Log.LogOrigin.FRONT_END)
                        .createdDate(new Date())
                        .description(log.getDescription())
                        .build()
        );
    }

    public Page<Log> getAll(int pageNumber, int pageSize){
        var sort = Sort.by("id").descending();
        var pageable = PageRequest.of(pageNumber, pageSize, sort);
        return logRepository.findAll(pageable);
    }
}
