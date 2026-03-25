package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository  extends JpaRepository<Teacher, Long> {
    Page<Teacher> findBySpeciality(CourseType speciality, Pageable pageable);
}
