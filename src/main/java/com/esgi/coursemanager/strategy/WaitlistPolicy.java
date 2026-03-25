package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("WAITLIST")
public class WaitlistPolicy implements EnrollmentPolicy {
    @Override
    public Result<Void> apply(Course course) {
        if (course.getEnrollments().size() >= 30)
            // C'est uniquement pour utiliser le pattern
            // La logique de waitlist serait à ajouter ici
            
            return Result.success();

        return Result.success();
    }
}