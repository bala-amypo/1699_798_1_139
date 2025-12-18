package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepo;
import com.example.demo.service.UniversityService;
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
            existing.setLocation(univ.getLocation());
            return universityRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("University not found with id " + id));
    }

    @Override
    public University getUniversityById(Long id) {
        return universityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found with id " + id));
    }

    @Override
    public List<University> getAllUniversities() {
        return universityRepo.findAll();
    }

    @Override
    public void deactivateUniversity(Long id) {
        University uni = getUniversityById(id);
        uni.setActive(false);
        universityRepo.save(uni);
    }
}
