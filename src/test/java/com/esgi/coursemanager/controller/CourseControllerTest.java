package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void shouldGetCourses() {
        var course = new Course();
        var course1 = new CourseDto(course);
        var course2 = new CourseDto(course);
        var queryDto = new CourseQueryDto();
        when(courseService.getCourses(queryDto)).thenReturn(Result.success(Arrays.asList(course1, course2)));

        var response = courseController.getCourses(queryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService, times(1)).getCourses(queryDto);
    }

    @Test
    void shouldGetCourses_whenNotFound() {
        var queryDto = new CourseQueryDto();
        when(courseService.getCourses(queryDto)).thenReturn(Result.failure(HttpStatus.NOT_FOUND, ""));

        var response = courseController.getCourses(queryDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService, times(1)).getCourses(queryDto);
    }

    @Test
    void shouldGetCourse() {
        var course = new Course();
        var courseDto = new CourseDto(course);
        when(courseService.getCourse(1L)).thenReturn(Result.success(courseDto));

        var response = courseController.getCourse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService, times(1)).getCourse(1L);
    }

    @Test
    void shouldGetCourseStudents() {
        var enrollment = new Enrollment();
        var courseDto = new CourseStudentDto(enrollment);
        when(courseService.getCourseStudents(1L)).thenReturn(Result.success(List.of(courseDto)));

        var response = courseController.getCourseStudents(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService, times(1)).getCourseStudents(1L);
    }

    @Test
    void shouldCreateCourse() {
        var courseDto = new CourseCreateBodyDto();
        when(courseService.createCourse(courseDto.getTitle(), courseDto.getDescription(), courseDto.getCourseType(), courseDto.getTeacherId())).thenReturn(Result.success(1L));

        var response = courseController.createCourse(courseDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(courseService, times(1)).createCourse(courseDto.getTitle(), courseDto.getDescription(), courseDto.getCourseType(), courseDto.getTeacherId());
    }

    @Test
    void shouldUpdateCourse() {
        var course = new Course();
        var dto = new CourseDto(course);
        var courseDto = new CourseUpdateBodyDto();
        when(courseService.updateCourse(1L, courseDto.getTitle(), courseDto.getDescription(), courseDto.getCourseType(), courseDto.getTeacherId())).thenReturn(Result.success(dto));

        var response = courseController.updateCourse(1L, courseDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService, times(1)).updateCourse(1L, courseDto.getTitle(), courseDto.getDescription(), courseDto.getCourseType(), courseDto.getTeacherId());
    }

    @Test
    void shouldDeleteCourse() {
        when(courseService.deleteCourse(1L)).thenReturn(Result.success());

        var response = courseController.deleteCourse(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(1L);
    }
}