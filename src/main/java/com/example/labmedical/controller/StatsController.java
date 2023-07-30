package com.example.labmedical.controller;

import com.example.labmedical.controller.dtos.response.*;
import com.example.labmedical.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
@RequiredArgsConstructor
public class StatsController {

    @Autowired
    private PacientService pacientService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ExamService examService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private DietService dietService;

    @Autowired
    private ExerciseService exerciseService;


    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse statsResponse = new StatsResponse();

        List<PacientResponse> pacientResponseList = pacientService.getPacients();
        statsResponse.setQtPatients(pacientResponseList.size());

        List<UserResponse> userResponseList = userService.getListUsers();
        statsResponse.setQtUsers(userResponseList.size());

        List<AppointmentResponse> appointmentResponseList = appointmentService.getAppointments(null);
        statsResponse.setQtAppointments(appointmentResponseList.size());

        List<ExamResponse> examResponseList = examService.getExams(null,null);
        statsResponse.setQtExams(examResponseList.size());

        List<MedicineResponse> medicineResponseList = medicineService.getMedicines(null);
        statsResponse.setQtMedicines(medicineResponseList.size());

        List<DietResponse> dietResponseList = dietService.getDiets(null);
        statsResponse.setQtDiets(dietResponseList.size());

        List<ExerciseResponse> exerciseResponseList = exerciseService.getAll();
        statsResponse.setQtExercises(exerciseResponseList.size());

        return ResponseEntity.ok(statsResponse);
    }

}
