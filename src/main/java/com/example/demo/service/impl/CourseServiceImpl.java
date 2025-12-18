package com.example.app.service.impl;

import com.example.app.entity.Course;
import com.example.app.repository.CourseRepo;
import com.example.app.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

 
    @Override
    public Course updateCourse(Long id, Course course) {
        return courseRepo.findById(id).map(existing -> {
            existing.setCourseName(course.getCourseName());
            existing.setCreditHours(course.getCreditHours());
            existing.setDepartment(course.getDepartment());
            existing.setUniversity(course.getUniversity());
            return courseRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return courseRepo.findByUniversityIdAndActiveTrue(universityId);
    }

    @Override
    public void deactivateCourse(Long id) {
        Course course = getCourseById(id);
        course.setActive(false);
        courseRepo.save(course);
    }

    @Override
    public Course createCourse(Course course) {
        return course;
        
    } 
}
