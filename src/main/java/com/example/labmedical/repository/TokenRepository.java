package com.example.labmedical.repository;

import com.example.labmedical.repository.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository  extends JpaRepository<Token, Long> {
}
