package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.*;
import com.esgi.coursemanager.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        var result = courseService.getCourse(id);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCourses(@ModelAttribute CourseQueryDto queryDto) {
        var result = courseService.getCourses(queryDto);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseCreateBodyDto dto) {
        var result = courseService.createCourse(
                dto.getTitle(),
                dto.getDescription(),
                dto.getCourseType(),
                dto.getTeacherId()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateBodyDto dto) {
        var result = courseService.updateCourse(
                id,
                dto.getTitle(),
                dto.getDescription(),
                dto.getCourseType(),
                dto.getTeacherId()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        var result = courseService.deleteCourse(id);

        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}
