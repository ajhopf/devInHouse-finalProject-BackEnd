package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {

    @Query("SELECT m FROM Medicine m where m.pacient.id = :pacientId")
    List<Medicine> getMedicinesByPacient_Id(@Param("pacientId") Long id);

}
