package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.University;
import java.util.Optional;
public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByName(String name);
}
