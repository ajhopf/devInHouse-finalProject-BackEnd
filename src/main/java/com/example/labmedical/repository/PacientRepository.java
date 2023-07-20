package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Long> {
    Boolean existsByEmailOrCpf(String email, String cpf);
}
