package com.esgi.coursemanager.controller;

import com.esgi.coursemanager.dto.StudentCreateBodyDto;
import com.esgi.coursemanager.dto.StudentQueryDto;
import com.esgi.coursemanager.dto.StudentUpdateBodyDto;
import com.esgi.coursemanager.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        var result = studentService.getStudent(id);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<?> getStudentEnrollments(@PathVariable Long id) {
        var result = studentService.getStudentEnrollments(id);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getStudents(@ModelAttribute StudentQueryDto queryDto) {
        var result = studentService.getStudents(queryDto);

        return result.toResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentCreateBodyDto dto) {
        var result = studentService.createStudent(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );

        return result.toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,
                                           @RequestBody StudentUpdateBodyDto dto) {
        var result = studentService.updateStudent(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );

        return result.toResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        var result = studentService.deleteStudent(id);

        return result.toResponseEntity(HttpStatus.NO_CONTENT);
    }
}
