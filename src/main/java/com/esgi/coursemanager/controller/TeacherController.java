package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.TeacherCreateBodyDto;
import com.esgi.coursemanager.dto.TeacherQueryDto;
import com.esgi.coursemanager.dto.TeacherUpdateBodyDto;
import com.esgi.coursemanager.service.TeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@Tag(name = "Teachers", description = "Teacher management APIs")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(
            summary = "Get a teacher by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher found"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(
            @Parameter(description = "Teacher id") @PathVariable Long id) {

        var result = teacherService.getTeacher(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get courses of a teacher",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of courses"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )
    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getTeacherCourses(
            @Parameter(description = "Teacher id") @PathVariable Long id) {

        var result = teacherService.getTeacherCourses(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get all teachers with filters",
            responses = @ApiResponse(responseCode = "200", description = "List of teachers")
    )
    @GetMapping
    public ResponseEntity<?> getTeachers(
            @Parameter(description = "Filters")
            @ModelAttribute TeacherQueryDto queryDto) {

        var result = teacherService.getTeachers(queryDto);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Create a teacher",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Teacher created"),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    @PostMapping
    public ResponseEntity<?> createTeacher(
            @Valid @RequestBody TeacherCreateBodyDto dto) {

        var result = teacherService.createTeacher(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCourseType()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a teacher",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher updated"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(
            @Parameter(description = "Teacher id") @PathVariable Long id,
            @RequestBody TeacherUpdateBodyDto dto) {

        var result = teacherService.updateTeacher(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCourseType()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a teacher",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Teacher deleted"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(
            @Parameter(description = "Teacher id") @PathVariable Long id) {

        var result = teacherService.deleteTeacher(id);
        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}