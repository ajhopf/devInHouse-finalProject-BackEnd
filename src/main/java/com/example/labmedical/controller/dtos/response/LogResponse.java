package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.repository.model.Log;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogResponse {
    private Long id;
    private String description;
    private Date createdDate;
    private Log.LogType logType;
    private Log.LogOrigin logOrigin;
}
