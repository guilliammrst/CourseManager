package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course, Long> {
}
