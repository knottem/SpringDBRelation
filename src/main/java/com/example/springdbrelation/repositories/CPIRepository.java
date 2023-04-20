package com.example.springdbrelation.repositories;

import com.example.springdbrelation.models.CPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CPIRepository extends JpaRepository<CPI, Long> {
}
