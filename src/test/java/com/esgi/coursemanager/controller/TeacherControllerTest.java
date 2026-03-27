package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.service.TeacherService;
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
public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @Test
    void shouldGetTeachers() {
        var teacher = new Teacher();
        var teacher1 = new TeacherDto(teacher);
        var teacher2 = new TeacherDto(teacher);
        var queryDto = new TeacherQueryDto();
        when(teacherService.getTeachers(queryDto)).thenReturn(Result.success(Arrays.asList(teacher1, teacher2)));

        var response = teacherController.getTeachers(queryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teacherService, times(1)).getTeachers(queryDto);
    }

    @Test
    void shouldGetTeacher() {
        var teacher = new Teacher();
        var teacher1 = new TeacherDto(teacher);
        when(teacherService.getTeacher(1L)).thenReturn(Result.success(teacher1));

        var response = teacherController.getTeacher(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teacherService, times(1)).getTeacher(1L);
    }

    @Test
    void shouldGetTeacherCourses() {
        var course = new Course();
        var teacherCourse = new TeacherCourseDto(course);
        when(teacherService.getTeacherCourses(1L)).thenReturn(Result.success(List.of(teacherCourse)));

        var response = teacherController.getTeacherCourses(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teacherService, times(1)).getTeacherCourses(1L);
    }

    @Test
    void shouldCreateTeacher() {
        var teacherCreate = new TeacherCreateBodyDto();
        when(teacherService.createTeacher(teacherCreate.getFirstName(), teacherCreate.getLastName(), teacherCreate.getCourseType())).thenReturn(Result.success(1L));

        var response = teacherController.createTeacher(teacherCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(teacherService, times(1)).createTeacher(teacherCreate.getFirstName(), teacherCreate.getLastName(), teacherCreate.getCourseType());
    }

    @Test
    void shouldUpdateTeacher() {
        var teacher = new Teacher();
        var teacher1 = new TeacherDto(teacher);
        var teacherUpdate = new TeacherUpdateBodyDto();
        when(teacherService.updateTeacher(1L, teacherUpdate.getFirstName(), teacherUpdate.getLastName(), teacherUpdate.getCourseType())).thenReturn(Result.success(teacher1));

        var response = teacherController.updateTeacher(1L, teacherUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teacherService, times(1)).updateTeacher(1L, teacherUpdate.getFirstName(), teacherUpdate.getLastName(), teacherUpdate.getCourseType());
    }

    @Test
    void shouldDeleteTeacher() {
        when(teacherService.deleteTeacher(1L)).thenReturn(Result.success());

        var response = teacherController.deleteTeacher(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(teacherService, times(1)).deleteTeacher(1L);
    }
}
