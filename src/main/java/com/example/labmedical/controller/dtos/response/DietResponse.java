package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.enums.DietType;
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
public class DietResponse {
    private Long id;
    private String dietName;
    private LocalDate dietDate;
    private LocalTime time;
    private DietType dietType;
    private String description;
    private Boolean status;
    private Long pacientId;
}
