package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepo;
import com.example.demo.service.UniversityService;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepo repository;

    public UniversityServiceImpl(UniversityRepo repository) {
        this.repository = repository;
    }

    @Override
    public University createUniversity(University univ) {
        repository.findByName(univ.getName())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("University name already exists");
                });

        univ.setActive(true);
        return repository.save(univ);
    }

    @Override
    public University updateUniversity(Long id, University univ) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        existing.setName(univ.getName());
        existing.setAccreditationLevel(univ.getAccreditationLevel());
        existing.setCountry(univ.getCountry());

        return repository.save(existing);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    @Override
    public List<University> getAllUniversities() {
        return repository.findAll();
    }

    @Override
    public void deactivateUniversity(Long id) {
        University university = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        university.setActive(false);
        repository.save(university);
    }
}
