package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository  extends JpaRepository<Course, Long> {
    Optional<Course> findById(long id);
}
