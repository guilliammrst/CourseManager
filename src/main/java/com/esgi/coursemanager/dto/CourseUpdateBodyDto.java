package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseUpdateBodyDto {

    private String title;

    private String description;

    private CourseType courseType;

    private Long teacherId;
}
