package com.example.demo.service;

import com.example.demo.entity.University;
import java.util.List;

public interface UniversityService {
    University createUniversity(University univ);
    University updateUniversity(Long id, University univ);
    University getUniversityById(Long id);
    List<University> getAllUniversities();
    void deactivateUniversity(Long id);
}
