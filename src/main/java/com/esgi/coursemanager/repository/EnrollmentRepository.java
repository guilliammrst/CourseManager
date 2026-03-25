package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
        boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
