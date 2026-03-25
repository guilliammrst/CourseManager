package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import org.springframework.stereotype.Component;

@Component("PRIORITY")
public class PriorityPolicy implements EnrollmentPolicy {
    @Override
    public Result<Void> apply(Course course) {
        return Result.success();
    }
}