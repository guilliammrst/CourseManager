package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherCreateBodyDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private CourseType courseType;
}
