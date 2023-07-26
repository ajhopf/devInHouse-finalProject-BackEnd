package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietRepository  extends JpaRepository<Diet, Long> {
    List<Diet> findAllByPacient_Id(Long id);
    List<Diet> findAllByPacient_NameContaining(String pacientName);
    boolean existsByPacient_Id(Long pacientId);
}
