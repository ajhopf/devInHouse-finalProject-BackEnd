package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.repository.model.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRequest {
    private String description;
    private Log.LogType logType;
}
