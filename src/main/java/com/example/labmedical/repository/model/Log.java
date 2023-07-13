package com.example.labmedical.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 8, max = 64)
    private String description;
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private LogType logType;
    @Enumerated(EnumType.STRING)
    private LogOrigin logOrigin;
    public enum LogType {
        SUCCESS, INFO, ERROR
    }
    public enum LogOrigin {
        FRONT_END, BACK_END
    }
}
