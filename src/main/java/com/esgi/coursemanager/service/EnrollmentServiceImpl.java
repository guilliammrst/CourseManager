package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.EnrollmentDto;
import com.esgi.coursemanager.model.Enrollment;
import com.esgi.coursemanager.model.EnrollmentStatus;
import com.esgi.coursemanager.repository.CourseRepository;
import com.esgi.coursemanager.repository.EnrollmentRepository;
import com.esgi.coursemanager.repository.StudentRepository;
import com.esgi.coursemanager.strategy.EnrollmentPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final Map<String, EnrollmentPolicy> policies;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentServiceImpl(Map<String, EnrollmentPolicy> policies,
                                 EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.policies = policies;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Result<Long> enrollStudent(Long studentId, Long courseId, String type) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId))
            return Result.failure(HttpStatus.BAD_REQUEST, "Student is already enrolled in this course.");

        // Search for the policy based on the type, default to NORMAL if not found
        var policy = policies.getOrDefault(type.toUpperCase(), policies.get("NORMAL"));

        var studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Student not found.");

        var student = studentOpt.get();

        var courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Course not found.");

        var course = courseOpt.get();

        var applyPolicyResult = policy.apply(course);
        if (!applyPolicyResult.isSuccess())
            return Result.failure(applyPolicyResult.getErrorStatus(), applyPolicyResult.getError());

        var enrollment = new Enrollment(LocalDate.now(), EnrollmentStatus.ACTIVE, student, course);

        var createdEnrollment = enrollmentRepository.save(enrollment);

        return Result.success(createdEnrollment.getId());
    }

    public Result<EnrollmentDto> updateEnrollmentStatus(Long id, EnrollmentStatus status) {
        var enrollmentOpt = enrollmentRepository.findById(id);
        if (enrollmentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Enrollment not found.");

        var enrollment = enrollmentOpt.get();
        enrollment.setStatus(status);

        enrollmentRepository.save(enrollment);

        var enrollmentDto = new EnrollmentDto(enrollment);

        return Result.success(enrollmentDto);
    }

    public Result<Void> deleteEnrollment(Long id) {
        var enrollmentOpt = enrollmentRepository.findById(id);
        if (enrollmentOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Enrollment not found.");

        var enrollment = enrollmentOpt.get();
        enrollmentRepository.delete(enrollment);

        return Result.success();
    }
}