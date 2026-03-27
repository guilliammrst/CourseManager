package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.Student;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void shouldGetStudents() {
        var student = new Student();
        var student1 = new StudentDto(student);
        var student2 = new StudentDto(student);
        var queryDto = new StudentQueryDto();
        when(studentService.getStudents(queryDto)).thenReturn(Result.success(Arrays.asList(student1, student2)));

        var response = studentController.getStudents(queryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).getStudents(queryDto);
    }

    @Test
    void shouldGetStudent() {
        var student = new Student();
        var studentDto = new StudentDto(student);
        when(studentService.getStudent(1L)).thenReturn(Result.success(studentDto));

        var response = studentController.getStudent(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).getStudent(1L);
    }

    @Test
    void shouldGetStudentEnrollments() {
        var enrollment = new Enrollment();
        var studentDto = new StudentEnrollmentDto(enrollment);
        when(studentService.getStudentEnrollments(1L)).thenReturn(Result.success(List.of(studentDto)));

        var response = studentController.getStudentEnrollments(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).getStudentEnrollments(1L);
    }

    @Test
    void shouldCreateStudent() {
        var studentCreateDto = new StudentCreateBodyDto();
        when(studentService.createStudent(studentCreateDto.getFirstName(), studentCreateDto.getLastName(), studentCreateDto.getEmail())).thenReturn(Result.success(1L));

        var response = studentController.createStudent(studentCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(studentService, times(1)).createStudent(studentCreateDto.getFirstName(), studentCreateDto.getLastName(), studentCreateDto.getEmail());
    }

    @Test
    void shouldUpdateStudent() {
        var student = new Student();
        var studentDto = new StudentDto(student);
        var studentUpdateDto = new StudentUpdateBodyDto();
        when(studentService.updateStudent(1L, studentUpdateDto.getFirstName(), studentUpdateDto.getLastName(), studentUpdateDto.getEmail())).thenReturn(Result.success(studentDto));

        var response = studentController.updateStudent(1L, studentUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService, times(1)).updateStudent(1L, studentUpdateDto.getFirstName(), studentUpdateDto.getLastName(), studentUpdateDto.getEmail());
    }

    @Test
    void shouldDeleteStudent() {
        when(studentService.deleteStudent(1L)).thenReturn(Result.success());

        var response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).deleteStudent(1L);
    }
}
