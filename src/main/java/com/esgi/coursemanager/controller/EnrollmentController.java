package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.EnrollmentBodyDto;
import com.esgi.coursemanager.dto.EnrollmentUpdateBodyDto;
import com.esgi.coursemanager.service.EnrollmentService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> enrollStudent(@Valid @RequestBody EnrollmentBodyDto dto) {
        var result = enrollmentService.enrollStudent(
                dto.getStudentId(),
                dto.getCourseId(),
                dto.getType()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollmentStatus(@PathVariable Long id,
                                                    @Valid @RequestBody EnrollmentUpdateBodyDto dto) {
        var result = enrollmentService.updateEnrollmentStatus(
                id,
                dto.getStatus());

        return result.toResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long id) {
        var result = enrollmentService.deleteEnrollment(id);

        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}