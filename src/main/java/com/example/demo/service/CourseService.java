package com.example.demo.service;

import com.example.demo.entity.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course);
    Course updateCourse(Long id, Course course);
    Course getCourseById(Long id); // Throw if not found
    List<Course> getCoursesByUniversity(Long universityId);
    void deactivateCourse(Long id); // mark as inactive
}
