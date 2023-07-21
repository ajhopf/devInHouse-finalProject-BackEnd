package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Alergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlergyRepository extends JpaRepository<Alergy, Long> {
}
