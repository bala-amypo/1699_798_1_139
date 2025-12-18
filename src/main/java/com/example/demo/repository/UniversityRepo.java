package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app.entity.University;

public interface UniversityRepo extends JpaRepository<University, Long> {
    University findByName(String name);
}
