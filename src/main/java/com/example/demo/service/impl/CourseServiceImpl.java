package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.University;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repo;

    @Autowired
    private UniversityRepository univRepo;

    @Override
    public Course createCourse(Course course) {

        if (course.getCreditHours() <= 0) {
            throw new IllegalArgumentException("> 0");
        }

        Long univId = course.getUniversity().getId();
        University u = univRepo.findById(univId)
                .orElseThrow(() -> new RuntimeException("not found"));

        repo.findByUniversityIdAndCourseCode(univId, course.getCourseCode())
                .ifPresent(c -> { throw new IllegalArgumentException("exists"); });

        course.setUniversity(u);
        return repo.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        if (course.getCourseName() != null) {
            existing.setCourseName(course.getCourseName());
        }
        return repo.save(existing);
    }

    @Override
    public Course getCourseById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public void deactivateCourse(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        c.setActive(false);
        repo.save(c);
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return repo.findByUniversityIdAndActiveTrue(universityId);
    }
}
