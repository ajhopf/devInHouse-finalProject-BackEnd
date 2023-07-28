package com.example.labmedical.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsResponse {

    private Integer qtPatients;

    private Integer qtUsers;

    private Integer qtAppointments;

    private Integer qtExams;

    private Integer qtMedicines;

    private Integer qtDiets;

    private Integer qtExercises;

}
