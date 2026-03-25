package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private CourseType speciality;

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.speciality = teacher.getSpeciality();
    }
}
