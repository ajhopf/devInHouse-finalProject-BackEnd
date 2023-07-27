package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> getExamsByPacient_Id(Long pacientId);
    @Query("SELECT e FROM Exam e WHERE e.pacient.name LIKE %:name%")
    List<Exam> findExamsByPacientNameLike(String name);

    boolean existsByPacient_Id(Long pacientId);
}
