package com.example.demo.controller;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<University> create(@Valid @RequestBody University university) {
        University created = service.createUniversity(university);
       
        return ResponseEntity
                .created(URI.create("/api/universities/" + created.getId()))
                .body(created);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<University> update(@PathVariable Long id, @Valid @RequestBody University university) {
        University updated = service.updateUniversity(id, university);
        return ResponseEntity.ok(updated);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<University> getById(@PathVariable Long id) {
        University univ = service.getUniversityById(id);
        return ResponseEntity.ok(univ);
    }

    @GetMapping
    public ResponseEntity<List<University>> getAll() {
        List<University> universities = service.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<University> deactivate(@PathVariable Long id) {
        University deactivated = service.deactivateUniversity(id);
        return ResponseEntity.ok(deactivated);
    }
}
