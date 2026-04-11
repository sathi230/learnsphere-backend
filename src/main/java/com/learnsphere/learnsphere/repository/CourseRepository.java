package com.learnsphere.learnsphere.repository;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTrainer(User trainer);
    Optional<Course> findByCourseName(String courseName);
}
