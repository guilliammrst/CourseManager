package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.CourseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course, Long> {
    Page<Course> findByCourseType(CourseType courseType, Pageable pageable);
}
