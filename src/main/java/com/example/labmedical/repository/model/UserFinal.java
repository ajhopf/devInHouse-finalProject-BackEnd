package com.example.labmedical.repository.model;

import com.example.labmedical.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users_final")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFinal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private String cpf;
    private String telephone;
    private String email;
    private String password;
    private Role type;
}
