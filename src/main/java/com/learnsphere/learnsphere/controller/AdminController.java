	package com.learnsphere.learnsphere.controller;
	
	
	import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.*;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.StudentCourse;
import com.learnsphere.learnsphere.entity.User;
import com.learnsphere.learnsphere.repository.CourseRepository;
import com.learnsphere.learnsphere.repository.StudentCourseRepository;
import com.learnsphere.learnsphere.repository.UserRepository;
	
	@RestController
	@RequestMapping("/api/admin")
	@CrossOrigin(origins = "http://localhost:3000")
	public class AdminController {
	
	    @Autowired
	    private CourseRepository courseRepository;
	
	    @Autowired
	    private UserRepository userRepository;
	    
	    
	    @Autowired
	    private StudentCourseRepository studentCourseRepository;
	    
	    
	
	
	    // TOTAL COURSES
	    @GetMapping("/totalCourses")
	    public long getTotalCourses() {
	        return courseRepository.count();
	    }
	   
	    
	    @GetMapping("/students")
	    public List<User> getStudents() {
	        return userRepository.findByRole("STUDENT");
	    }
	
	    // TOTAL STUDENTS
	    @GetMapping("/totalStudents")
	    public long getTotalStudents() {
	        return userRepository.countByRole("STUDENT");
	    }
	
	
	    // TOTAL TRAINERS
	    @GetMapping("/totalTrainers")
	    public long getTotalTrainers() {
	        return userRepository.countByRole("TRAINER");
	    }
	    @GetMapping("/trainers")
	    public List<User> getTrainers() {
	        return userRepository.findByRole("TRAINER"); // returns List<User>
	    }
	    @GetMapping("/courses")
	    public List<Course> getAllCourses() {
	        return courseRepository.findAll();
	    }
	    
	    
	    @PostMapping("/assign-course")
	    public String assignCourse(@RequestBody Map<String, Object> body) {
	        // Safe conversion to String
	        String studentName = String.valueOf(body.get("studentName"));
	        String mobile = String.valueOf(body.get("mobile"));
	        String courseName = String.valueOf(body.get("courseName"));

	        if (studentName.isEmpty() || mobile.isEmpty() || courseName.isEmpty()) {
	            return "All fields are required";
	        }

	        // 1️⃣ Check if student exists, else create
	        User student = userRepository.findByNameAndMobile(studentName, mobile)
	                .orElseGet(() -> {
	                    User u = new User();
	                    u.setName(studentName);
	                    u.setMobile(mobile);
	                    u.setRole("STUDENT");
	                    u.setPassword("default123");
	                    String dummyEmail = studentName.replaceAll(" ", "") + mobile + "@learnsphere.com";
	                    u.setEmail(dummyEmail);// default password
	                    return userRepository.save(u);
	                });

	        // 2️⃣ Find course by name
	        Course course = courseRepository.findByCourseName(courseName)
	                .orElseThrow(() -> new RuntimeException("Course not found"));

	        // 3️⃣ Assign course
	        StudentCourse sc = new StudentCourse();
	        sc.setStudent(student);
	        sc.setCourse(course);
	        sc.setPaid(true);

	        studentCourseRepository.save(sc);

	        return "Course Assigned Successfully";
	    }
	}