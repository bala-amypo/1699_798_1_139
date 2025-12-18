package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CourseContentTopic;

public interface CourseContentTopicService {
 CourseContentTopic createTopic(CourseContentTopic topic);
CourseContentTopic updateTopic(Long id, CourseContentTopic topic);
CourseContentTopic getTopicById(Long id);
    List<CourseContentTopic> getTopicsForCourse(Long courseId);
} 
