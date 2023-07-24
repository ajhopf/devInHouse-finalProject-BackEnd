package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository  extends JpaRepository<Diet, Long> {
}
