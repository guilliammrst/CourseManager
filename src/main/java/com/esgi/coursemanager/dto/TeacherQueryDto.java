package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherQueryDto extends PaginationDto {

    private CourseType teacherSpeciality;
}
