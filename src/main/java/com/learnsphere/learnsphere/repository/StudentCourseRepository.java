package com.learnsphere.learnsphere.repository;

import com.learnsphere.learnsphere.entity.StudentCourse;
import com.learnsphere.learnsphere.entity.User;
import com.learnsphere.learnsphere.entity.Course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository
        extends JpaRepository<StudentCourse, Long> {

    static List<StudentCourse> findByStudent(User student) {
		// TODO Auto-generated method stub
		return null;
	}

    Optional<StudentCourse> findByStudentAndCourse(User student, Course course);
    List<StudentCourse> findByStudentId(Long studentId);
}
