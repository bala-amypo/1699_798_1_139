package com.example.demo.controller;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "Course Content Topic API")
public class CourseContentTopicController {

    @Autowired
    private CourseContentTopicService service;

    @PostMapping
    public CourseContentTopic create(@RequestBody CourseContentTopic topic) {
        return service.createTopic(topic);
    }

    @PutMapping("/{id}")
    public CourseContentTopic update(@PathVariable Long id, @RequestBody CourseContentTopic topic) {
        return service.updateTopic(id, topic);
    }

    @GetMapping("/{id}")
    public CourseContentTopic getById(@PathVariable Long id) {
        return service.getTopicById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getByCourse(@PathVariable Long courseId) {
        return service.getTopicsForCourse(courseId);
    }
}
