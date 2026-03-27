package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Test
    void shouldEnrollStudents() {
        var dto = new EnrollmentBodyDto();
        when(enrollmentService.enrollStudent(dto.getStudentId(), dto.getCourseId(), dto.getType())).thenReturn(Result.success(1L));

        var response = enrollmentController.enrollStudent(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(enrollmentService, times(1)).enrollStudent(dto.getStudentId(), dto.getCourseId(), dto.getType());
    }

    @Test
    void shouldDeleteEnrollment() {
        when(enrollmentService.deleteEnrollment(1L)).thenReturn(Result.success());

        var response = enrollmentController.deleteEnrollment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }

    @Test
    void shouldUpdateEnrollmentStatus() {
        var dto = new EnrollmentUpdateBodyDto();
        var enrollment = new Enrollment();
        var enrollmentDto = new EnrollmentDto(enrollment);
        when(enrollmentService.updateEnrollmentStatus(1L, dto.getStatus())).thenReturn(Result.success(enrollmentDto));

        var response = enrollmentController.updateEnrollmentStatus(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(enrollmentService, times(1)).updateEnrollmentStatus(1L, dto.getStatus());
    }
}
