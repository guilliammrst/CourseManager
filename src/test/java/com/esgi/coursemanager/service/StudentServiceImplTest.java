package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.StudentDto;
import com.esgi.coursemanager.dto.StudentEnrollmentDto;
import com.esgi.coursemanager.dto.StudentQueryDto;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.Student;
import com.esgi.coursemanager.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    // -----------------------
    // getStudent
    // -----------------------

    @Test
    void shouldReturnStudent_whenExists() {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Result<StudentDto> result = studentService.getStudent(1L);

        assertTrue(result.isSuccess());
        assertEquals("John", result.getData().getFirstName());
    }

    @Test
    void shouldReturn404_whenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Result<StudentDto> result = studentService.getStudent(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // getStudentEnrollments
    // -----------------------

    @Test
    void shouldReturnEnrollments_whenStudentExists() {
        var student = new Student("Jane", "Doe", "jane@example.com");
        student.setId(1L);
        student.setEnrollments(List.of(new Enrollment()));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Result<List<StudentEnrollmentDto>> result = studentService.getStudentEnrollments(1L);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
    }

    @Test
    void shouldReturn404_whenStudentNotFoundForEnrollments() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Result<List<StudentEnrollmentDto>> result = studentService.getStudentEnrollments(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // getStudents
    // -----------------------

    @Test
    void shouldReturnAllStudents_whenNoEmailFilter() {
        var student = new Student("John", "Doe", "john@example.com");
        var page = new PageImpl<>(List.of(student));

        when(studentRepository.findAll(any(Pageable.class))).thenReturn(page);

        var query = new StudentQueryDto();
        query.setPage(0);
        query.setSize(10);

        var result = studentService.getStudents(query);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
    }

    @Test
    void shouldReturnFilteredStudents_whenEmailProvided() {
        var student = new Student("John", "Doe", "john@example.com");
        var page = new PageImpl<>(List.of(student));

        when(studentRepository.findByEmail(eq("john@example.com"), any(Pageable.class))).thenReturn(page);

        var query = new StudentQueryDto();
        query.setPage(0);
        query.setSize(10);
        query.setEmail("john@example.com");

        var result = studentService.getStudents(query);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
    }

    // -----------------------
    // createStudent
    // -----------------------

    @Test
    void shouldCreateStudent_whenEmailNotUsed() {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        var result = studentService.createStudent("John", "Doe", "john@example.com");

        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData());
    }

    @Test
    void shouldReturn400_whenEmailIsInvalid() {
        var result = studentService.createStudent("John", "Doe", null);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, result.getErrorStatus());
    }

    @Test
    void shouldReturn400_whenEmailAlreadyUsed() {
        when(studentRepository.existsByEmail("john@example.com")).thenReturn(true);

        var result = studentService.createStudent("John", "Doe", "john@example.com");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, result.getErrorStatus());
    }

    // -----------------------
    // updateStudent
    // -----------------------

    @Test
    void shouldUpdateStudent_whenExists() {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        var result = studentService.updateStudent(1L, "Jane", "APA", "jane@example.com");

        assertTrue(result.isSuccess());
        assertEquals("Jane", result.getData().getFirstName());
        assertEquals("APA", result.getData().getLastName());
        assertEquals("jane@example.com", result.getData().getEmail());
    }

    @Test
    void shouldReturn400UpdateStudent_whenEmailIsInvalid() {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        var result = studentService.updateStudent(1L, null, null, "jane.com");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, result.getErrorStatus());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'   '",
            "null"
    }, nullValues = "null")
    void shouldUpdateStudent_whenNothingToUpdate(String update) {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        var result = studentService.updateStudent(1L, update, update, update);

        assertTrue(result.isSuccess());
        assertEquals("John", result.getData().getFirstName());
        assertEquals("Doe", result.getData().getLastName());
        assertEquals("john@example.com", result.getData().getEmail());
    }

    @Test
    void shouldReturn404_whenStudentNotFoundForUpdate() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        var result = studentService.updateStudent(1L, "Jane", null, "jane@example.com");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    // -----------------------
    // deleteStudent
    // -----------------------

    @Test
    void shouldDeleteStudent_whenExists() {
        var student = new Student("John", "Doe", "john@example.com");
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        var result = studentService.deleteStudent(1L);

        assertTrue(result.isSuccess());
        verify(studentRepository).delete(student);
    }

    @Test
    void shouldReturn404_whenStudentNotFoundForDelete() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        var result = studentService.deleteStudent(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }
}