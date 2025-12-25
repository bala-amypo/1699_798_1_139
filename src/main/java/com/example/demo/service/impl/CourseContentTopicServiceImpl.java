package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {

    @Autowired
    private CourseContentTopicRepository repo;
    @Autowired
    private CourseRepository courseRepo;

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {

        if (topic.getTopicName() == null || topic.getTopicName().trim().isEmpty()) {
            throw new IllegalArgumentException("Topic name");
        }

        if (topic.getWeightPercentage() < 0 || topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("0-100");
        }

        Course c = courseRepo.findById(topic.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("not found"));

        topic.setCourse(c);
        return repo.save(topic);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        CourseContentTopic existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        return repo.save(existing);
    }

    @Override
    public CourseContentTopic getTopicById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("not found"));
        return repo.findByCourseId(courseId);
    }
}
