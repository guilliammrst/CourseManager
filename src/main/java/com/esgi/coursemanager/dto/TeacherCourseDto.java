package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.CourseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherCourseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private CourseType courseType;

    public TeacherCourseDto(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.courseType = course.getCourseType();
    }
}
