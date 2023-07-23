package com.example.labmedical.controller.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResponse {
    private Long id;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private LocalTime time;
    private String type;
    private String laboratory;
    private String documentUrl;
    private String result;
    private Boolean status;
    private Long pacientId;
}
