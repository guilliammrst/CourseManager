package com.esgi.coursemanager.strategy;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriorityPolicyTest {

    private final PriorityPolicy priorityPolicy = new PriorityPolicy();

    @Test
    void shouldAllowEnrollment_always() {
        var course = new Course();
        course.setEnrollments(new ArrayList<>());

        Result<Void> result = priorityPolicy.apply(course);

        assertTrue(result.isSuccess());
    }
}
