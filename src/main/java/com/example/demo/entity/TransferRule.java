package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer_rules")
public class TransferRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_university_id")
    private University sourceUniversity;

    @ManyToOne(optional = false)
      @JoinColumn(name = "target_university_id")
    private University targetUniversity;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Double minimumOverlapPercentage;

    @Column(nullable = false)
    @Min(0)
    private Integer creditHourTolerance;

    @Column(nullable = false)
    private Boolean active = true;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public University getSourceUniversity() { return sourceUniversity; }
    public void setSourceUniversity(University sourceUniversity) { this.sourceUniversity = sourceUniversity; }

    public University getTargetUniversity() { return targetUniversity; }
    public void setTargetUniversity(University targetUniversity) { this.targetUniversity = targetUniversity; }

    public Double getMinimumOverlapPercentage() { return minimumOverlapPercentage; }
    public void setMinimumOverlapPercentage(Double minimumOverlapPercentage) {
        if(minimumOverlapPercentage < 0 || minimumOverlapPercentage > 100) throw new IllegalArgumentException("minimumOverlapPercentage 0-100");
        this.minimumOverlapPercentage = minimumOverlapPercentage;
    }

    public Integer getCreditHourTolerance() { return creditHourTolerance; }
    public void setCreditHourTolerance(Integer creditHourTolerance) {
        if(creditHourTolerance < 0) throw new IllegalArgumentException("creditHourTolerance >= 0");
        this.creditHourTolerance = creditHourTolerance;
    }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
