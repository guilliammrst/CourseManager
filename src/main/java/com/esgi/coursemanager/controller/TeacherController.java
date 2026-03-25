package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.TeacherCreateBodyDto;
import com.esgi.coursemanager.dto.TeacherQueryDto;
import com.esgi.coursemanager.dto.TeacherUpdateBodyDto;
import com.esgi.coursemanager.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable Long id) {
        var result = teacherService.getTeacher(id);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getTeacherCourses(@PathVariable Long id) {
        var result = teacherService.getTeacherCourses(id);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTeachers(@ModelAttribute TeacherQueryDto queryDto) {
        var result = teacherService.getTeachers(queryDto);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherCreateBodyDto dto) {
        var result = teacherService.createTeacher(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCourseType()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherUpdateBodyDto dto) {
        var result = teacherService.updateTeacher(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCourseType()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        var result = teacherService.deleteTeacher(id);

        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}