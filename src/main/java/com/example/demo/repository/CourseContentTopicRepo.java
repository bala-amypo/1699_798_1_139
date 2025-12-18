package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.entity.CourseContentTopic;

public interface CourseContentTopicRepo
        extends JpaRepository<CourseContentTopic, Long> {

    List<CourseContentTopic> findByCourseId(Long courseId);
}
