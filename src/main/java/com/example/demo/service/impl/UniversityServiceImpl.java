package com.example.app.service.impl;

import com.example.app.entity.University;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.repository.UniversityRepo;
import com.example.app.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepo universityRepo;

    @Override
    public University createUniversity(University univ) {
        univ.setActive(true);
        return universityRepo.save(univ);
    }

    @Override
    public University updateUniversity(Long id, University univ) {
        return universityRepo.findById(id).map(existing -> {
            existing.setName(univ.getName());
            return universityRepo.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
    }

    @Override
    public University getUniversityById(Long id) {
        return universityRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
    }

    @Override
    public List<University> getAllUniversities() {
        return universityRepo.findAll();
    }

    @Override
    public void deactivateUniversity(Long id) {
       University uni = universityRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
        uni.setActive(false);
        universityRepo.save(uni);
    }
}
