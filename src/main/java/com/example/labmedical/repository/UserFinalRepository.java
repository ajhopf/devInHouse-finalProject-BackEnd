package com.example.labmedical.repository;

import com.example.labmedical.repository.model.UserFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserFinalRepository extends JpaRepository<UserFinal, Long> {
}
