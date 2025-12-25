// package com.example.demo.service.impl;

// import com.example.demo.entity.University;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.UniversityRepository;
// import com.example.demo.service.UniversityService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class UniversityServiceImpl implements UniversityService {

//     @Autowired
//     private UniversityRepository universityRepo;

//     @Override
//     public University createUniversity(University univ) {
//         univ.setActive(true);
//         return universityRepo.save(univ);
//     }

//     @Override
//     public University updateUniversity(Long id, University univ) {
//         return universityRepo.findById(id).map(existing -> {
//             existing.setName(univ.getName());
//             return universityRepo.save(existing);
//         }).orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
//     }

//     @Override
//     public University getUniversityById(Long id) {
//         return universityRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
//     }

//     @Override
//     public List<University> getAllUniversities() {
//         return universityRepo.findAll();
//     }

//     @Override
//     public void deactivateUniversity(Long id) {
//        University uni = universityRepo.findById(id)
//             .orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));
//         uni.setActive(false);
//         universityRepo.save(uni);
//     }
// }

// package com.example.demo.service.impl;

// import com.example.demo.entity.University;
// import com.example.demo.repository.UniversityRepository;
// import com.example.demo.service.UniversityService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class UniversityServiceImpl implements UniversityService {

//     // injected by reflection in tests
//     private UniversityRepository repository;

//     @Override
//     public University createUniversity(University university) {

//         if (university == null || university.getName() == null || university.getName().trim().isEmpty()) {
//             throw new IllegalArgumentException("Name required");
//         }

//         repository.findByName(university.getName()).ifPresent(u -> {
//             throw new IllegalArgumentException("exists");
//         });

//         return repository.save(university);
//     }

//     @Override
//     public University updateUniversity(Long id, University university) {
//         University existing = repository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("not found"));

//         if (university.getName() != null) {
//             existing.setName(university.getName());
//         }
//         return repository.save(existing);
//     }

//     @Override
//     public University getUniversityById(Long id) {
//         return repository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("not found"));
//     }

//     @Override
//     public void deactivateUniversity(Long id) {
//         University u = repository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("not found"));
//         u.setActive(false);
//         repository.save(u);
//     }

//     @Override
//     public List<University> getAllUniversities() {
//         return repository.findAll();
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository repository;

    // ✅ Constructor injection
    public UniversityServiceImpl(UniversityRepository repository) {
        this.repository = repository;
    }

    @Override
    public University createUniversity(University university) {
        repository.findByName(university.getName())
                .ifPresent(u -> { throw new IllegalArgumentException("University with name exists"); });
        return repository.save(university);
    }

    @Override
    public University updateUniversity(Long id, University university) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        existing.setName(university.getName());
        existing.setLocation(university.getLocation());
        return repository.save(existing);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Override
    public List<University> getAllUniversities() {
        return repository.findAll();
    }

    @Override
    public void deactivateUniversity(Long id) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        existing.setActive(false);
        repository.save(existing);
          return "University deactivated successfully";
    }
}
