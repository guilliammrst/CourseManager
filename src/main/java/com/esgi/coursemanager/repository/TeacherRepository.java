package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository  extends JpaRepository<Teacher, Long> {
}
