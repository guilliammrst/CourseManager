package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.StudentDto;
import com.esgi.coursemanager.dto.StudentEnrollmentDto;
import com.esgi.coursemanager.dto.StudentQueryDto;

import java.util.List;

public interface StudentService {
    Result<StudentDto> getStudent(Long id);
    Result<List<StudentEnrollmentDto>> getStudentEnrollments(Long id);
    Result<List<StudentDto>> getStudents(StudentQueryDto queryDto);
    Result<Long> createStudent(String firstName, String lastName, String email);
    Result<StudentDto> updateStudent(Long id, String firstName, String lastName, String email);
    Result<Void> deleteStudent(Long id);
}
