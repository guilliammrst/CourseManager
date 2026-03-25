package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherCreateBodyDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private CourseType courseType;
}
