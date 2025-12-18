package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.example.demo.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findByUniversityIdAndCourseCode(Long universityId, String courseCode);

    List<Course> findByUniversityIdAndActiveTrue(Long universityId);
}
