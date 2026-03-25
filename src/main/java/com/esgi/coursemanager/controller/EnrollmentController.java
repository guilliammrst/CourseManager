package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.EnrollmentBodyDto;
import com.esgi.coursemanager.dto.EnrollmentUpdateBodyDto;
import com.esgi.coursemanager.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@Tag(name = "Enrollments", description = "Enrollment management APIs")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Operation(
            summary = "Enroll a student to a course",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student enrolled"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "404", description = "Student or course not found")
            }
    )
    @PostMapping
    public ResponseEntity<?> enrollStudent(@Valid @RequestBody EnrollmentBodyDto dto) {

        var result = enrollmentService.enrollStudent(
                dto.getStudentId(),
                dto.getCourseId(),
                dto.getType()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update enrollment status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Enrollment updated"),
                    @ApiResponse(responseCode = "404", description = "Enrollment not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollmentStatus(
            @Parameter(description = "Enrollment id")
            @PathVariable Long id,

            @Valid @RequestBody EnrollmentUpdateBodyDto dto) {

        var result = enrollmentService.updateEnrollmentStatus(
                id,
                dto.getStatus()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete an enrollment",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Enrollment deleted"),
                    @ApiResponse(responseCode = "404", description = "Enrollment not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(
            @Parameter(description = "Enrollment id")
            @PathVariable Long id) {

        var result = enrollmentService.deleteEnrollment(id);
        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}