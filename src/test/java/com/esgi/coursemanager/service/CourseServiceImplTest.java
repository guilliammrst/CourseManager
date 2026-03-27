package com.esgi.coursemanager.service;

import com.esgi.coursemanager.dto.CourseQueryDto;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.repository.CourseRepository;
import com.esgi.coursemanager.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    // -----------------------
    // getCourse
    // -----------------------

    @Test
    void shouldReturnCourse_whenCourseExists() {
        var course = new Course("Java", "Intro", CourseType.BIOLOGY, null);
        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        var result = courseService.getCourse(1L);

        assertTrue(result.isSuccess());
        assertEquals("Java", result.getData().getTitle());
    }

    @Test
    void shouldReturn404_whenCourseNotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = courseService.getCourse(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // getCourseStudents
    // -----------------------

    @Test
    void shouldReturnStudents_whenCourseExists() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);
        course.setId(1L);

        course.setEnrollments(List.of()); // simple cas

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        var result = courseService.getCourseStudents(1L);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldReturn404_whenCourseStudentsCourseNotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = courseService.getCourseStudents(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // getCourses
    // -----------------------

    @Test
    void shouldReturnAllCourses_whenNoFilter() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);

        var page = new PageImpl<>(List.of(course));

        when(courseRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        var query = new CourseQueryDto();
        query.setPage(0);
        query.setSize(10);

        var result = courseService.getCourses(query);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
    }

    @Test
    void shouldReturnFilteredCourses_whenCourseTypeProvided() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);

        var page = new PageImpl<>(List.of(course));

        when(courseRepository.findByCourseType(eq(CourseType.ART), any(Pageable.class)))
                .thenReturn(page);

        var query = new CourseQueryDto();
        query.setPage(0);
        query.setSize(10);
        query.setCourseType(CourseType.ART);

        var result = courseService.getCourses(query);

        assertTrue(result.isSuccess());
    }

    // -----------------------
    // createCourse
    // -----------------------

    @Test
    void shouldCreateCourse_withoutTeacher() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);
        course.setId(1L);

        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);

        var result = courseService.createCourse(
                "Java", "Intro", CourseType.ART, null
        );

        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData());
    }

    @Test
    void shouldCreateCourse_withTeacher() {
        var teacher = new Teacher();
        teacher.setId(1L);

        Course course = new Course("Java", "Intro", CourseType.ART, teacher);
        course.setId(1L);

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(teacher));

        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);

        var result = courseService.createCourse(
                "Java", "Intro", CourseType.ART, 1L
        );

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldReturn404_whenTeacherNotFound_createCourse() {
        when(teacherRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = courseService.createCourse(
                "Java", "Intro", CourseType.ART, 1L
        );

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // updateCourse
    // -----------------------

    @Test
    void shouldUpdateCourse_basicFields() {
        Course course = new Course("Old", "Old", CourseType.ART, null);
        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(courseRepository.save(any()))
                .thenReturn(course);

        var result = courseService.updateCourse(
                1L, "New", "New desc", CourseType.BIOLOGY, null
        );

        assertTrue(result.isSuccess());
        assertEquals("New", result.getData().getTitle());
    }

    @Test
    void shouldUpdateCourse_onlyCourseType() {
        Course course = new Course("Old", "Old", CourseType.ART, null);
        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(courseRepository.save(any()))
                .thenReturn(course);

        var result = courseService.updateCourse(
                1L, "   ", " ", CourseType.BIOLOGY, null
        );

        assertTrue(result.isSuccess());
        assertEquals("Old", result.getData().getTitle());
        assertEquals("Old", result.getData().getDescription());
        assertEquals("Old", result.getData().getDescription());
    }

    @Test
    void shouldRemoveTeacher_whenTeacherIdZero() {
        Teacher teacher = new Teacher();
        Course course = new Course("Java", "Intro", CourseType.ART, teacher);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(courseRepository.save(any()))
                .thenReturn(course);

        var result = courseService.updateCourse(
                1L, null, null, null, 0L
        );

        assertTrue(result.isSuccess());
        assertNull(course.getTeacher());
    }

    @Test
    void shouldUpdateTeacher_whenValidTeacher() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);
        Teacher teacher = new Teacher();
        teacher.setId(2L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(teacherRepository.findById(2L))
                .thenReturn(Optional.of(teacher));

        when(courseRepository.save(any()))
                .thenReturn(course);

        var result = courseService.updateCourse(
                1L, null, null, null, 2L
        );

        assertTrue(result.isSuccess());
        assertEquals(2L, course.getTeacher().getId());
    }

    @Test
    void shouldReturn404_whenTeacherNotFound_update() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(teacherRepository.findById(2L))
                .thenReturn(Optional.empty());

        var result = courseService.updateCourse(
                1L, null, null, null, 2L
        );

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldReturn404_whenCourseNotFound_update() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = courseService.updateCourse(
                1L, "New", "Desc", CourseType.ART, null
        );

        assertFalse(result.isSuccess());
    }

    // -----------------------
    // deleteCourse
    // -----------------------

    @Test
    void shouldDeleteCourse_whenExists() {
        Course course = new Course("Java", "Intro", CourseType.ART, null);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        var result = courseService.deleteCourse(1L);

        assertTrue(result.isSuccess());
        verify(courseRepository).delete(course);
    }

    @Test
    void shouldReturn404_whenDeleteCourseNotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = courseService.deleteCourse(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }
}