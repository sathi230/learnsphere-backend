package com.learnsphere.learnsphere.controller;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.Lesson;
import com.learnsphere.learnsphere.repository.CourseRepository;
import com.learnsphere.learnsphere.repository.LessonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    // ================= GET LESSONS BY COURSE =================
    // API used by React dashboard
    // URL example: /api/lessons/course/1

    @GetMapping("/course/{courseId}")
    public List<Lesson> getLessonsByCourse(@PathVariable Long courseId) {

        // find course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // fetch lessons for that course
        return lessonRepository.findByCourse(course);
    }
}