package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.CourseDto;
import com.esgi.coursemanager.dto.CourseQueryDto;
import com.esgi.coursemanager.model.CourseType;

import java.util.List;

public interface CourseService {
    Result<CourseDto> getCourse(Long id);
    Result<List<CourseDto>> getCourses(CourseQueryDto queryDto);
    Result<Long> createCourse(String title, String description, CourseType courseType, Long teacherId);
    Result<CourseDto> updateCourse(Long id, String title, String description, CourseType courseType, Long teacherId);
    Result<Void> deleteCourse(Long id);
}
