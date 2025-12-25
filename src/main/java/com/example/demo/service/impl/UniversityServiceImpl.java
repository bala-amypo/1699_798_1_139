package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    // injected by reflection in tests
    @Autowired
    private UniversityRepository repository;

    @Override
    public University createUniversity(University university) {

        if (university == null || university.getName() == null || university.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }

        repository.findByName(university.getName()).ifPresent(u -> {
            throw new IllegalArgumentException("exists");
        });

        return repository.save(university);
    }

    @Override
    public University updateUniversity(Long id, University university) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        if (university.getName() != null) {
            existing.setName(university.getName());
        }
        return repository.save(existing);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public void deactivateUniversity(Long id) {
        University u = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        u.setActive(false);
        repository.save(u);
    }

    @Override
    public List<University> getAllUniversities() {
        return repository.findAll();
    }
}

