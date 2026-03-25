package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;

public interface EnrollmentPolicy {
    Result<Void> apply(Course course);
}