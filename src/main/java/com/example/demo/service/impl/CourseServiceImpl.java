// package com.example.demo.service.impl;

// import com.example.demo.entity.Course;
// import com.example.demo.repository.CourseRepository;
// import com.example.demo.service.CourseService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.exception.ResourceNotFoundException;

// import java.util.List;

// @Service
// public class CourseServiceImpl implements CourseService {

//     @Autowired
//     private CourseRepository courseRepo;

//      @Override
// public Course createCourse(Course course) {
//     return courseRepo.save(course);
// }

//    @Override
// public Course updateCourse(Long id, Course course) {
//     return courseRepo.findById(id)
//             .map(existing -> {

//                 if (course.getCourseName() != null)
//                     existing.setCourseName(course.getCourseName());

//                 if (course.getCreditHours() != null)
//                     existing.setCreditHours(course.getCreditHours());

//                 if (course.getDepartment() != null)
//                     existing.setDepartment(course.getDepartment());

//                 if (course.getActive() != null)
//                     existing.setActive(course.getActive());

//                 return courseRepo.save(existing);

//             }).orElseThrow(() ->
//                     new ResourceNotFoundException("Course not found with id " + id));
// }


//     @Override
//     public Course getCourseById(Long id) {
//         return courseRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
//     }

//     @Override
//     public List<Course> getCoursesByUniversity(Long universityId) {
//         return courseRepo.findByUniversityIdAndActiveTrue(universityId);
//     }

//     @Override
//     public void deactivateCourse(Long id) {
//         Course course = getCourseById(id);
//         course.setActive(false);
//         courseRepo.save(course);
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

 
    @Override
    public Course updateCourse(Long id, Course course) {
        return courseRepo.findById(id).map(existing -> {
            existing.setCourseName(course.getCourseName());
            existing.setCreditHours(course.getCreditHours());
            existing.setDepartment(course.getDepartment());
            existing.setUniversity(course.getUniversity());
            return courseRepo.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return courseRepo.findByUniversityIdAndActiveTrue(universityId);
    }

    @Override
    public void deactivateCourse(Long id) {
        Course course = getCourseById(id);
        course.setActive(false);
        courseRepo.save(course);
    }

    @Override
    public Course createCourse(Course course) {
    return courseRepo.save(course); 
        
    }

   
}
