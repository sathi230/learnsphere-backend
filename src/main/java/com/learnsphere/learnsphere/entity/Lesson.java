package com.learnsphere.learnsphere.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    private String lessonName;
    private String lessonTopic;
    private String lessonVideoLink;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getLessonTopic() {
		return lessonTopic;
	}

	public void setLessonTopic(String lessonTopic) {
		this.lessonTopic = lessonTopic;
	}

	public String getLessonVideoLink() {
		return lessonVideoLink;
	}

	public void setLessonVideoLink(String lessonVideoLink) {
		this.lessonVideoLink = lessonVideoLink;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	

    // Getters and Setters
}
