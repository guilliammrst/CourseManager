package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.TeacherDto;
import com.esgi.coursemanager.dto.TeacherQueryDto;
import com.esgi.coursemanager.model.CourseType;

import java.util.List;

public interface TeacherService {
    Result<TeacherDto> getTeacher(Long id);
    Result<List<TeacherDto>> getTeachers(TeacherQueryDto queryDto);
    Result<Long> createTeacher(String firstName, String lastName, CourseType courseType);
    Result<TeacherDto> updateTeacher(Long id, String firstName, String lastName, CourseType courseType);
    Result<Void> deleteTeacher(Long id);
}
