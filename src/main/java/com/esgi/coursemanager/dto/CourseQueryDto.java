package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.CourseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseQueryDto extends PaginationDto {

    public CourseType courseType;
}
