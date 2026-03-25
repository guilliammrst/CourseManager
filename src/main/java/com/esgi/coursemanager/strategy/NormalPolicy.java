package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("NORMAL")
public class NormalPolicy implements EnrollmentPolicy {
    @Override
    public Result<Void> apply(Course course) {
        if (course.getEnrollments().size() >= 30)
            return Result.failure(HttpStatus.BAD_REQUEST, "Course is full. Cannot enroll student.");

        return Result.success();
    }
}