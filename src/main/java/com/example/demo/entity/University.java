package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "universities", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String accreditationLevel;
    private String country;


    private Boolean active = true;

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { 
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name exists");
        this.name = name; 
    }

    public String getAccreditationLevel() { return accreditationLevel; }
    public void setAccreditationLevel(String accreditationLevel) { this.accreditationLevel = accreditationLevel; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
   
}
