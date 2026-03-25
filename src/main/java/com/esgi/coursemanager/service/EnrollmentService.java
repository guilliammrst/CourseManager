package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.EnrollmentDto;
import com.esgi.coursemanager.model.EnrollmentStatus;

public interface EnrollmentService {
    Result<Long> enrollStudent(Long studentId, Long courseId, String type);
    Result<EnrollmentDto> updateEnrollmentStatus(Long id, EnrollmentStatus status);
    Result<Void> deleteEnrollment(Long id);
}
