package com.example.springdbrelation.repositories;

import com.example.springdbrelation.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
