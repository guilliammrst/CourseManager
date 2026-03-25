package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseCreateBodyDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private CourseType courseType;

    private Long teacherId;
}
