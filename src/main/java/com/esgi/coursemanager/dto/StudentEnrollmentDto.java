package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentEnrollmentDto {

    @NotNull
    private Long id;

    @NotNull
    private LocalDate enrollmentDate;

    @NotNull
    private EnrollmentStatus status;

    @NotNull
    private Long courseId;

    public StudentEnrollmentDto(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.enrollmentDate = enrollment.getEnrollmentDate();
        this.status = enrollment.getStatus();

        var course = enrollment.getCourse();
        if (course != null)
            this.courseId = course.getId();
    }
}
