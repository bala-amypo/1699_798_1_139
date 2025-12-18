package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.University;

public interface UniversityRepo extends JpaRepository<University, Long> {
    University findByName(String name);
}
