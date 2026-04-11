package com.learnsphere.learnsphere.controller;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.StudentCourse;
import com.learnsphere.learnsphere.repository.CourseRepository;
import com.learnsphere.learnsphere.repository.StudentCourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    // ================= VIEW ALL COURSES =================
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courses = courseRepository.findAll();

        return ResponseEntity.ok(courses);
    }
    @GetMapping("/{studentId}/courses")
    public List<Course> getStudentCourses(@PathVariable Long studentId) {
        List<StudentCourse> scList = studentCourseRepository.findByStudentId(studentId);
        return scList.stream()
                     .map(StudentCourse::getCourse)
                     .collect(Collectors.toList());
    }
}
