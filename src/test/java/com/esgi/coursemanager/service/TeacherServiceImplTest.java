package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.TeacherCourseDto;
import com.esgi.coursemanager.dto.TeacherDto;
import com.esgi.coursemanager.dto.TeacherQueryDto;
import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void shouldReturnTeacher_whenExists() {
        var teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setSpeciality(CourseType.MATHS);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Result<TeacherDto> result = teacherService.getTeacher(1L);

        assertTrue(result.isSuccess());
        assertEquals("John", result.getData().getFirstName());
        assertEquals("Doe", result.getData().getLastName());
        assertEquals(CourseType.MATHS, result.getData().getSpeciality());
    }

    @Test
    void shouldReturnNotFound_whenTeacherDoesNotExist() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Result<TeacherDto> result = teacherService.getTeacher(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldCreateTeacherSuccessfully() {
        var teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Jane");
        teacher.setLastName("Doe");
        teacher.setSpeciality(CourseType.PHYSICS);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Result<Long> result = teacherService.createTeacher("Jane", "Doe", CourseType.PHYSICS);

        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData());

        ArgumentCaptor<Teacher> captor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(captor.capture());
        assertEquals("Jane", captor.getValue().getFirstName());
        assertEquals("Doe", captor.getValue().getLastName());
        assertEquals(CourseType.PHYSICS, captor.getValue().getSpeciality());
    }

    @Test
    void shouldUpdateTeacher_whenFieldsAreNotNull() {
        var teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setSpeciality(CourseType.MATHS);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Result<TeacherDto> result = teacherService.updateTeacher(1L, "Jane", "Smith", CourseType.PHYSICS);

        assertTrue(result.isSuccess());
        assertEquals("Jane", result.getData().getFirstName());
        assertEquals("Smith", result.getData().getLastName());
        assertEquals(CourseType.PHYSICS, result.getData().getSpeciality());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'   '",
            "null"
    }, nullValues = "null")
    void shouldNotUpdateTeacher_whenNothingToUpdate(String update) {
        var teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setSpeciality(CourseType.MATHS);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Result<TeacherDto> result = teacherService.updateTeacher(1L, update, update, null);

        assertTrue(result.isSuccess());
        assertEquals("John", result.getData().getFirstName());
        assertEquals("Doe", result.getData().getLastName());
        assertEquals(CourseType.MATHS, result.getData().getSpeciality());
    }

    @Test
    void shouldReturnNotFound_whenUpdatingNonExistingTeacher() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Result<TeacherDto> result = teacherService.updateTeacher(1L, "Jane", "Smith", CourseType.PHYSICS);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldDeleteTeacherSuccessfully() {
        when(teacherRepository.existsById(1L)).thenReturn(true);

        Result<Void> result = teacherService.deleteTeacher(1L);

        assertTrue(result.isSuccess());
        verify(teacherRepository).deleteById(1L);
    }

    @Test
    void shouldReturnNotFound_whenDeletingNonExistingTeacher() {
        when(teacherRepository.existsById(1L)).thenReturn(false);

        Result<Void> result = teacherService.deleteTeacher(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldReturnTeacherCourses() {
        var teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Result<List<TeacherCourseDto>> result = teacherService.getTeacherCourses(1L);

        assertTrue(result.isSuccess());
        assertEquals(teacher.getCourses().size(), result.getData().size());
    }

    @Test
    void shouldReturnNotFound_whenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Result<List<TeacherCourseDto>> result = teacherService.getTeacherCourses(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldReturnTeachersBySpeciality() {
        var teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setSpeciality(CourseType.MATHS);

        var teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setSpeciality(CourseType.MATHS);

        var page = new PageImpl<>(List.of(teacher1, teacher2));

        when(teacherRepository.findBySpeciality(eq(CourseType.MATHS), any(Pageable.class))).thenReturn(page);

        var queryDto = new TeacherQueryDto();
        queryDto.setPage(0);
        queryDto.setSize(10);
        queryDto.setTeacherSpeciality(CourseType.MATHS);

        Result<List<TeacherDto>> result = teacherService.getTeachers(queryDto);

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
    }

    @Test
    void shouldReturnTeachers() {
        var teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setSpeciality(CourseType.MATHS);

        var teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setSpeciality(CourseType.BIOLOGY);

        var page = new PageImpl<>(List.of(teacher1, teacher2));

        when(teacherRepository.findAll(any(Pageable.class))).thenReturn(page);

        var queryDto = new TeacherQueryDto();
        queryDto.setPage(0);
        queryDto.setSize(10);

        Result<List<TeacherDto>> result = teacherService.getTeachers(queryDto);

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
    }
}