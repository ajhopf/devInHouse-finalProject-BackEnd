package com.example.labmedical.repository;

import com.example.labmedical.repository.model.SpecialCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialCareRepository extends JpaRepository<SpecialCare, Long> {
    List<SpecialCare> getSpecialCaresByPacient_Id(Long id);
}
