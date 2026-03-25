package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;

public interface EnrollmentService {
    Result<Long> enrollStudent(Long studentId, Long courseId, String type);
    Result<Void> deleteEnrollment(Long id);
}
