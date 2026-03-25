package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.Student;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseStudentDto {

    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private LocalDate enrollmentDate;

    public CourseStudentDto(Enrollment enrollment) {
        this.id = enrollment.getStudent().getId();
        this.firstName = enrollment.getStudent().getFirstName();
        this.lastName = enrollment.getStudent().getLastName();
        this.email = enrollment.getStudent().getEmail();
        this.enrollmentDate = enrollment.getEnrollmentDate();
    }
}
