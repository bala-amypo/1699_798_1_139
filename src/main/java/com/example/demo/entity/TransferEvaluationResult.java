// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.sql.Timestamp;

// @Entity
// @Table(name = "transfer_evaluation_results")
// public class TransferEvaluationResult {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(optional = false)
//     private Course sourceCourse;

//     @ManyToOne(optional = false)
//     private Course targetCourse;

//     private Double overlapPercentage;

//     private Integer creditHourDifference;

//     private Boolean isEligibleForTransfer;

//     @Column(nullable = false)
//     private Timestamp evaluatedAt;

//     private String notes;

//     @PrePersist
//     protected void onCreate() {
//         this.evaluatedAt = new Timestamp(System.currentTimeMillis());
//     }

//     public Long getId() {
//         return id;
//     }

//     public Course getSourceCourse() {
//         return sourceCourse;
//     }

//     public void setSourceCourse(Course sourceCourse) {
//         this.sourceCourse = sourceCourse;
//     }

//     public Course getTargetCourse() {
//         return targetCourse;
//     }

//     public void setTargetCourse(Course targetCourse) {
//         this.targetCourse = targetCourse;
//     }

//     public Double getOverlapPercentage() {
//         return overlapPercentage;
//     }

//     public void setOverlapPercentage(Double overlapPercentage) {
//         this.overlapPercentage = overlapPercentage;
//     }

//     public Integer getCreditHourDifference() {
//         return creditHourDifference;
//     }

//     public void setCreditHourDifference(Integer creditHourDifference) {
//         this.creditHourDifference = creditHourDifference;
//     }

//     public Boolean getIsEligibleForTransfer() {
//         return isEligibleForTransfer;
//     }

//     public void setIsEligibleForTransfer(Boolean isEligibleForTransfer) {
//         this.isEligibleForTransfer = isEligibleForTransfer;
//     }

//     public Timestamp getEvaluatedAt() {
//         return evaluatedAt;
//     }

//     public String getNotes() {
//         return notes;
//     }

//     public void setNotes(String notes) {
//         this.notes = notes;
//     }
// }
package com.example.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_evaluation_results")
public class TransferEvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
      private Long courseId;

    @ManyToOne(optional = false)
    private Course sourceCourse;

    @ManyToOne(optional = false)
    private Course targetCourse;

    private Double overlapPercentage;
    private Integer creditHourDifference;
    private Boolean isEligibleForTransfer;

    @Column(nullable = false)
    private LocalDateTime evaluatedAt = LocalDateTime.now();

    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCourseId() { return courseId; }
public void setCourseId(Long courseId) { this.courseId = courseId; }

    public Course getSourceCourse() { return sourceCourse; }
    public void setSourceCourse(Course sourceCourse) { this.sourceCourse = sourceCourse; }

    public Course getTargetCourse() { return targetCourse; }
    public void setTargetCourse(Course targetCourse) { this.targetCourse = targetCourse; }

    public Double getOverlapPercentage() { return overlapPercentage; }
    public void setOverlapPercentage(Double overlapPercentage) { this.overlapPercentage = overlapPercentage; }

    public Integer getCreditHourDifference() { return creditHourDifference; }
    public void setCreditHourDifference(Integer creditHourDifference) { this.creditHourDifference = creditHourDifference; }

    public Boolean getIsEligibleForTransfer() { return isEligibleForTransfer; }
    public void setIsEligibleForTransfer(Boolean isEligibleForTransfer) { this.isEligibleForTransfer = isEligibleForTransfer; }

    public LocalDateTime getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(LocalDateTime evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
}
