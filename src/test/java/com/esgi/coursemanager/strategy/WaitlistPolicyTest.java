package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.Enrollment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaitlistPolicyTest {

    private final WaitlistPolicy waitlistPolicy = new WaitlistPolicy();

    @Test
    void shouldAllowEnrollment_whenCourseIsEmpty() {
        var course = new Course();
        course.setEnrollments(new ArrayList<>());

        Result<Void> result = waitlistPolicy.apply(course);

        assertTrue(result.isSuccess(), "Enrollment should be allowed when course is empty");
    }

    @Test
    void shouldAllowEnrollment_whenCourseIsFull() {
        var course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            enrollments.add(new Enrollment());
        }
        course.setEnrollments(enrollments);

        Result<Void> result = waitlistPolicy.apply(course);

        assertTrue(result.isSuccess(), "Enrollment should still be allowed even if course is full (waitlist)");
    }
}