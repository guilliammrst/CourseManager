package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@Tag(name = "Courses", description = "Course management APIs")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(
            summary = "Get a course by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Course found"),
                    @ApiResponse(responseCode = "404", description = "Course not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(
            @Parameter(description = "Course id") @PathVariable Long id) {

        var result = courseService.getCourse(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get students of a course",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of students"),
                    @ApiResponse(responseCode = "404", description = "Course not found")
            }
    )
    @GetMapping("/{id}/students")
    public ResponseEntity<?> getCourseStudents(
            @Parameter(description = "Course id") @PathVariable Long id) {

        var result = courseService.getCourseStudents(id);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Get all courses with filters",
            responses = @ApiResponse(responseCode = "200", description = "List of courses")
    )
    @GetMapping
    public ResponseEntity<?> getCourses(
            @Parameter(description = "Filters") @ModelAttribute CourseQueryDto queryDto) {

        var result = courseService.getCourses(queryDto);
        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Create a course",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Course created"),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    @PostMapping
    public ResponseEntity<?> createCourse(
            @Valid @RequestBody CourseCreateBodyDto dto) {

        var result = courseService.createCourse(
                dto.getTitle(),
                dto.getDescription(),
                dto.getCourseType(),
                dto.getTeacherId()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a course",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Course updated"),
                    @ApiResponse(responseCode = "404", description = "Course not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @Parameter(description = "Course id") @PathVariable Long id,
            @RequestBody CourseUpdateBodyDto dto) {

        var result = courseService.updateCourse(
                id,
                dto.getTitle(),
                dto.getDescription(),
                dto.getCourseType(),
                dto.getTeacherId()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a course",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Course deleted"),
                    @ApiResponse(responseCode = "404", description = "Course not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(
            @Parameter(description = "Course id") @PathVariable Long id) {

        var result = courseService.deleteCourse(id);
        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}
