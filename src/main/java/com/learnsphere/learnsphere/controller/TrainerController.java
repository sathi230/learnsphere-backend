package com.learnsphere.learnsphere.controller;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.Lesson;
import com.learnsphere.learnsphere.entity.User;
import com.learnsphere.learnsphere.repository.CourseRepository;
import com.learnsphere.learnsphere.repository.LessonRepository;
import com.learnsphere.learnsphere.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainer")
@CrossOrigin(origins = "http://localhost:3000")
public class TrainerController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    // ================= ADD COURSE =================
    @PostMapping("/add-course")
    public ResponseEntity<?> addCourse(@RequestBody Map<String, String> request) {

        String courseName = request.get("courseName");
        double coursePrice = Double.parseDouble(request.get("coursePrice"));
        String trainerEmail = request.get("trainerEmail");  // <-- new

        User trainer = userRepository.findByEmail(trainerEmail); // fetch trainer by email
        if(trainer == null) return ResponseEntity.badRequest().body("Trainer not found");

        Course course = new Course();
        course.setCourseName(courseName);
        course.setCoursePrice(coursePrice);
        course.setTrainer(trainer);

        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }
    @GetMapping("/my-courses")
    public ResponseEntity<?> getMyCourses(@RequestParam String email) {
        User trainer = userRepository.findByEmail(email);
        if(trainer == null) return ResponseEntity.badRequest().body("Trainer not found");

        List<Course> courses = courseRepository.findByTrainer(trainer);
        return ResponseEntity.ok(courses);
    }

   
    
    // ================= ADD LESSON =================
    @PostMapping("/add-lesson")
    public ResponseEntity<?> addLesson(@RequestBody Map<String, Object> request) {

        Long courseId = Long.valueOf(request.get("courseId").toString());

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Course not found");
        }

        Lesson lesson = new Lesson();
        lesson.setLessonName((String) request.get("lessonName"));
        lesson.setLessonTopic((String) request.get("lessonTopic"));
        lesson.setLessonVideoLink((String) request.get("lessonVideoLink"));
             lesson.setCourse(courseOpt.get());

        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseEntity.ok(savedLesson);
    }

    // ================= VIEW LESSONS BY COURSE =================
    @GetMapping("/lessons/{courseId}")
    public ResponseEntity<?> getLessons(@PathVariable Long courseId) {

        Optional<Course> courseOpt =
                courseRepository.findById(courseId);

        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Course not found");
        }

        List<Lesson> lessons =
                lessonRepository.findByCourse(courseOpt.get());

        return ResponseEntity.ok(lessons);
    }
}
