// package com.example.demo.controller;

// import com.example.demo.entity.University;
// import com.example.demo.service.UniversityService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import java.util.List;

// @RestController
// @RequestMapping("/api/universities")
// @Tag(name = "University API")
// public class UniversityController {

//     @Autowired
//     private UniversityService service;

//     @PostMapping
//     public University create(@RequestBody University university) {
//         return service.createUniversity(university);
//     }

//     @PutMapping("/{id}")
//     public University update(@PathVariable Long id, @RequestBody University university) {
//         return service.updateUniversity(id, university);
//     }

//     @GetMapping("/{id}")
//     public University getById(@PathVariable Long id) {
//         return service.getUniversityById(id);
//     }

//     @GetMapping
//     public List<University> getAll() {
//         return service.getAllUniversities();
//     }

//     @PutMapping("/{id}/deactivate")
//     public String deactivate(@PathVariable Long id) {
//         service.deactivateUniversity(id);
//         return "University deactivated successfully";
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
@Tag(name = "University API")
public class UniversityController {

    private final UniversityService service;

    // ✅ Constructor injection
    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<University> create(@RequestBody University university) {
        University saved = service.createUniversity(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<University> update(@PathVariable Long id, @RequestBody University university) {
        University updated = service.updateUniversity(id, university);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUniversityById(id));
    }

    @GetMapping
    public ResponseEntity<List<University>> getAll() {
        return ResponseEntity.ok(service.getAllUniversities());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivate(@PathVariable Long id) {
        service.deactivateUniversity(id);
        return ResponseEntity.ok("University deactivated successfully");
    }
}
