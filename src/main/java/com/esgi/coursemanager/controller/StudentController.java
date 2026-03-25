package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.StudentCreateBodyDto;
import com.esgi.coursemanager.dto.StudentQueryDto;
import com.esgi.coursemanager.dto.StudentUpdateBodyDto;
import com.esgi.coursemanager.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Students", description = "Student management APIs")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Get a student by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student found"),
                    @ApiResponse(responseCode = "404", description = "Student not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(
            @Parameter(description = "Student id") @PathVariable Long id) {

        var result = studentService.getStudent(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get enrollments of a student",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of enrollments"),
                    @ApiResponse(responseCode = "404", description = "Student not found")
            }
    )
    @GetMapping("/{id}/enrollments")
    public ResponseEntity<?> getStudentEnrollments(
            @Parameter(description = "Student id") @PathVariable Long id) {

        var result = studentService.getStudentEnrollments(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get all students with filters",
            responses = @ApiResponse(responseCode = "200", description = "List of students")
    )
    @GetMapping
    public ResponseEntity<?> getStudents(
            @Parameter(description = "Filters")
            @ModelAttribute StudentQueryDto queryDto) {

        var result = studentService.getStudents(queryDto);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Create a student",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student created"),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    @PostMapping
    public ResponseEntity<?> createStudent(
            @Valid @RequestBody StudentCreateBodyDto dto) {

        var result = studentService.createStudent(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a student",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student updated"),
                    @ApiResponse(responseCode = "404", description = "Student not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @Parameter(description = "Student id") @PathVariable Long id,
            @RequestBody StudentUpdateBodyDto dto) {

        var result = studentService.updateStudent(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a student",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Student deleted"),
                    @ApiResponse(responseCode = "404", description = "Student not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(
            @Parameter(description = "Student id") @PathVariable Long id) {

        var result = studentService.deleteStudent(id);
        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}