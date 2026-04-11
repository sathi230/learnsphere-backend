package com.learnsphere.learnsphere.repository;

import com.learnsphere.learnsphere.entity.Lesson;
import com.learnsphere.learnsphere.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse(Course course);
}
