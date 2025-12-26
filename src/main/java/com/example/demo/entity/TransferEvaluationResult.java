package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class TransferEvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sourceCourseId;
    private Long targetCourseId;
    private Double overlapPercentage;
    private Boolean isEligibleForTransfer;
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSourceCourseId() { return sourceCourseId; }
    public void setSourceCourseId(Long sourceCourseId) {
        this.sourceCourseId = sourceCourseId;
    }

    public Long getTargetCourseId() { return targetCourseId; }
    public void setTargetCourseId(Long targetCourseId) {
        this.targetCourseId = targetCourseId;
    }

    public Double getOverlapPercentage() { return overlapPercentage; }
    public void setOverlapPercentage(Double overlapPercentage) {
        this.overlapPercentage = overlapPercentage;
    }

    public Boolean getIsEligibleForTransfer() {
        return isEligibleForTransfer;
    }

    public void setIsEligibleForTransfer(Boolean eligible) {
        this.isEligibleForTransfer = eligible;
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
