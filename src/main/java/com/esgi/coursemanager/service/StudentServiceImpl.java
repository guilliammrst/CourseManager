package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.EmailHelper;
import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.StudentDto;
import com.esgi.coursemanager.dto.StudentEnrollmentDto;
import com.esgi.coursemanager.dto.StudentQueryDto;
import com.esgi.coursemanager.model.Student;
import com.esgi.coursemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Result<StudentDto> getStudent(Long id) {
        var studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Student not found.");

        var student = studentOpt.get();
        var studentDto = new StudentDto(student);

        return Result.success(studentDto);
    }

    public Result<List<StudentEnrollmentDto>> getStudentEnrollments(Long id) {
        var studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Student not found.");

        var student = studentOpt.get();
        var studentEnrollmentsDto = student.getEnrollments().stream()
                .map(StudentEnrollmentDto::new)
                .toList();

        return Result.success(studentEnrollmentsDto);
    }

    public Result<List<StudentDto>> getStudents(StudentQueryDto queryDto) {
        Pageable pageable = PageRequest.of(
                queryDto.getPage(),
                queryDto.getSize()
        );

        Page<Student> students;
        var email = queryDto.getEmail();

        if (email != null)
            students = studentRepository.findByEmail(email, pageable);
        else
            students = studentRepository.findAll(pageable);

        var studentsDto = students.stream()
                .map(StudentDto::new)
                .toList();

        return Result.success(studentsDto);
    }

    public Result<Long> createStudent(String firstName, String lastName, String email) {
        if (!EmailHelper.isValidEmail(email))
            return Result.failure(HttpStatus.BAD_REQUEST, "Invalid email format.");

        if (studentRepository.existsByEmail(email))
            return Result.failure(HttpStatus.BAD_REQUEST, "Email already used.");

        var student = new Student(firstName, lastName, email);

        var createdStudent = studentRepository.save(student);

        return Result.success(createdStudent.getId());
    }

    public Result<StudentDto> updateStudent(Long id, String firstName, String lastName, String email) {
        var studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Student not found.");

        var student = studentOpt.get();

        if (firstName != null && !firstName.isBlank())
            student.setFirstName(firstName);

        if (lastName != null && !lastName.isBlank())
            student.setLastName(lastName);

        if (email != null && !email.isBlank())
            if (EmailHelper.isValidEmail(email))
                student.setEmail(email);
            else
                return Result.failure(HttpStatus.BAD_REQUEST, "Invalid email format.");

        studentRepository.save(student);

        var studentDto = new StudentDto(student);

        return Result.success(studentDto);
    }

    public Result<Void> deleteStudent(Long id) {
        var studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Student not found.");

        var student = studentOpt.get();
        studentRepository.delete(student);

        return Result.success();
    }
}
