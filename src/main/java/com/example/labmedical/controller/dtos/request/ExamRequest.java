package com.example.labmedical.controller.dtos.request;

import com.example.labmedical.repository.model.Pacient;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamRequest {
    @NotBlank(message = "Campo 'nome' não pode estar em branco")
    @Size(min = 8, max = 64, message = "'nome' deve ter entre 8 e 64 caracteres")
    private String name;
    @NotNull(message = "É necessário inserir uma data para o exame.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate examDate;
    @NotNull(message = "É necessário inserir um horário.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    @NotBlank(message = "Campo 'tipo' não pode estar em branco")
    @Size(min = 4, max = 34, message = "'tipo' deve ter entre 4 e 34 caracteres")
    private String type;
    @NotBlank(message = "Campo 'laboratório' não pode estar em branco")
    @Size(min = 4, max = 34, message = "'laboratório' deve ter entre 4 e 34 caracteres")
    private String laboratory;
    private String documentUrl;
    @NotBlank(message = "Campo 'resultado' não pode estar em branco")
    @Size(min = 16, max = 1024, message = "'resultado' deve ter entre 16 e 1024 caracteres")
    private String result;
    @NotNull
    private Boolean status;
    private Long pacientId;
}
