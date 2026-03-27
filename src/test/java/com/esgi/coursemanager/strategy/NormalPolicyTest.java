package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormalPolicyTest {

    private final NormalPolicy normalPolicy = new NormalPolicy();

    @Test
    void shouldAllowEnrollment_whenCourseIsNotFull() {
        var course = new Course();
        course.setEnrollments(new ArrayList<>());

        Result<Void> result = normalPolicy.apply(course);

        assertTrue(result.isSuccess(), "Enrollment should be allowed when course is not full");
    }

    @Test
    void shouldRejectEnrollment_whenCourseIsFull() {
        var course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            enrollments.add(new Enrollment());

        course.setEnrollments(enrollments);

        Result<Void> result = normalPolicy.apply(course);

        assertFalse(result.isSuccess(), "Enrollment should be rejected when course is full");
        assertEquals(HttpStatus.BAD_REQUEST, result.getErrorStatus());
        assertEquals("Course is full. Cannot enroll student.", result.getError());
    }
}