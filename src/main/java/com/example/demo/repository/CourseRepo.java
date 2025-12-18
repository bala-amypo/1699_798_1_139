package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.app.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {

    Course findByUniversityIdAndCourseCode(Long universityId, String courseCode);

    List<Course> findByUniversityIdAndActiveTrue(Long universityId);
}
