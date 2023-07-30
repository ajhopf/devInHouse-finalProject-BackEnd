package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Alergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlergyRepository extends JpaRepository<Alergy, Long> {
    List<Alergy> getAlergiesByPacient_Id(Long id);
}
