package com.example.demo.service.impl;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {

    private final CourseContentTopicRepository repo;

    public CourseContentTopicServiceImpl(CourseContentTopicRepository repo) {
        this.repo = repo;
    }

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {
        return repo.save(topic);
    }

    @Override
    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        return repo.findByCourseId(courseId);
    }

    @Override
    public CourseContentTopic getTopicById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        CourseContentTopic existing = repo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setTopicName(topic.getTopicName());
        existing.setWeightPercentage(topic.getWeightPercentage());
        return repo.save(existing);
    }
}
