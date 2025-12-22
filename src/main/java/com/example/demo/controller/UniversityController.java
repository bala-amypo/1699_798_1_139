package com.example.demo.controller;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
@Tag(name = "University API")
public class UniversityController {

    @Autowired
    private UniversityService service;

    @PostMapping
    public University create(@RequestBody University university) {
        return service.createUniversity(university);
    }

    @PutMapping("/{id}")
    public University update(@PathVariable Long id, @RequestBody University university) {
        return service.updateUniversity(id, university);
    }

    @GetMapping("/{id}")
    public University getById(@PathVariable Long id) {
        return service.getUniversityById(id);
    }

    @GetMapping
    public List<University> getAll() {
        return service.getAllUniversities();
    }

    @PutMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        service.deactivateUniversity(id);
        return "University deactivated successfully";
    }
}
