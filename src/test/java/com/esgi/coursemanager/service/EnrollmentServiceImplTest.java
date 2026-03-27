package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.EnrollmentStatus;
import com.esgi.coursemanager.model.Student;
import com.esgi.coursemanager.repository.CourseRepository;
import com.esgi.coursemanager.repository.EnrollmentRepository;
import com.esgi.coursemanager.repository.StudentRepository;
import com.esgi.coursemanager.strategy.EnrollmentPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private Map<String, EnrollmentPolicy> policies;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    // -----------------------
    // enrollStudent
    // -----------------------

    @Test
    void shouldReturnBadRequest_whenStudentAlreadyEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        var result = enrollmentService.enrollStudent(1L, 1L, "NORMAL");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, result.getErrorStatus());
    }

    @Test
    void shouldReturnNotFound_whenStudentNotExist() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(policies.getOrDefault(eq("NORMAL"), any())).thenReturn(mock(EnrollmentPolicy.class));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        var result = enrollmentService.enrollStudent(1L, 1L, "NORMAL");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldReturnNotFound_whenCourseNotExist() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);

        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        var result = enrollmentService.enrollStudent(1L, 1L, "NORMAL");

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldReturnFailure_whenPolicyFails() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);

        EnrollmentPolicy policy = mock(EnrollmentPolicy.class);
        when(policy.apply(any(Course.class))).thenReturn(Result.failure(HttpStatus.BAD_REQUEST, "Policy failed"));
        when(policies.getOrDefault(eq("NORMAL"), any())).thenReturn(policy);

        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Course course = new Course();
        course.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        var result = enrollmentService.enrollStudent(1L, 1L, "NORMAL");

        assertFalse(result.isSuccess());
        assertEquals("Policy failed", result.getError());
    }

    @Test
    void shouldEnrollStudent_whenAllGood() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);

        EnrollmentPolicy policy = mock(EnrollmentPolicy.class);
        when(policy.apply(any(Course.class))).thenReturn(Result.success());
        when(policies.getOrDefault(eq("NORMAL"), any())).thenReturn(policy);

        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Course course = new Course();
        course.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Enrollment enrollment = new Enrollment(LocalDate.now(), EnrollmentStatus.ACTIVE, student, course);
        enrollment.setId(10L);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        var result = enrollmentService.enrollStudent(1L, 1L, "NORMAL");

        assertTrue(result.isSuccess());
        assertEquals(10L, result.getData());
    }

    // -----------------------
    // updateEnrollmentStatus
    // -----------------------

    @Test
    void shouldReturnNotFound_whenEnrollmentNotExist() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.empty());

        var result = enrollmentService.updateEnrollmentStatus(1L, EnrollmentStatus.CANCELLED);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldUpdateEnrollmentStatus_whenExists() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);

        var result = enrollmentService.updateEnrollmentStatus(1L, EnrollmentStatus.CANCELLED);

        assertTrue(result.isSuccess());
        assertEquals(EnrollmentStatus.CANCELLED, result.getData().getStatus());
    }

    // -----------------------
    // deleteEnrollment
    // -----------------------

    @Test
    void shouldReturnNotFound_whenDeleteNotExist() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.empty());

        var result = enrollmentService.deleteEnrollment(1L);

        assertFalse(result.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, result.getErrorStatus());
    }

    @Test
    void shouldDeleteEnrollment_whenExists() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        var result = enrollmentService.deleteEnrollment(1L);

        assertTrue(result.isSuccess());
        verify(enrollmentRepository).delete(enrollment);
    }
}