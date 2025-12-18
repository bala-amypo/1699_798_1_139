package com.example.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_content_topics")
public class CourseContentTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Course course;

    @Column(nullable = false)
    private String topicName;

    @Column(nullable = false)
    private Double weightPercentage;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) {
        if(topicName == null || topicName.isBlank()) throw new IllegalArgumentException("Topic name cannot be empty");
        this.topicName = topicName;
    }

    public Double getWeightPercentage() { return weightPercentage; }
    public void setWeightPercentage(Double weightPercentage) {
        if(weightPercentage < 0 || weightPercentage > 100) throw new IllegalArgumentException("weightPercentage 0-100");
        this.weightPercentage = weightPercentage;
    }
}
