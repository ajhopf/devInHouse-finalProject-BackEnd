package com.example.labmedical.repository.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Table(name = "config")
@Schema
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config {
    @Id
    private String key;
    @Column(length = 4000)
    private String value;
}
