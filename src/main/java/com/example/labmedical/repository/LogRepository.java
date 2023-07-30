package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findAll(Pageable pageable);
}
