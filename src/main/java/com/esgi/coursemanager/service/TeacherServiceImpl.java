package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.TeacherCourseDto;
import com.esgi.coursemanager.dto.TeacherDto;
import com.esgi.coursemanager.dto.TeacherQueryDto;
import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl (TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Result<TeacherDto> getTeacher(Long id) {
        var teacherOpt = teacherRepository.findById(id);
        if (teacherOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

        var teacher = teacherOpt.get();
        var teacherDto = new TeacherDto(teacher);

        return Result.success(teacherDto);
    }

    public Result<List<TeacherCourseDto>> getTeacherCourses(Long id) {
        var teacherOpt = teacherRepository.findById(id);
        if (teacherOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

        var teacher = teacherOpt.get();
        var teacherCoursesDto = teacher.getCourses().stream()
                .map(TeacherCourseDto::new)
                .toList();

        return Result.success(teacherCoursesDto);
    }

    public Result<List<TeacherDto>> getTeachers(TeacherQueryDto queryDto) {
        Pageable pageable = PageRequest.of(
                queryDto.getPage(),
                queryDto.getSize()
        );

        Page<Teacher> teachers;
        var teacherSpeciality = queryDto.getTeacherSpeciality();

        if (teacherSpeciality != null)
            teachers = teacherRepository.findBySpeciality(teacherSpeciality, pageable);
        else
            teachers = teacherRepository.findAll(pageable);

        var teachersDto = teachers.stream()
                .map(TeacherDto::new)
                .toList();

        return Result.success(teachersDto);
    }

    public Result<Long> createTeacher(String firstName, String lastName, CourseType courseType) {
        var teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setSpeciality(courseType);

        var savedTeacher = teacherRepository.save(teacher);

        return Result.success(savedTeacher.getId());
    }

    public Result<TeacherDto> updateTeacher(Long id, String firstName, String lastName, CourseType courseType) {
        var teacherOpt = teacherRepository.findById(id);

        if (teacherOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

        var teacher = teacherOpt.get();

        if (firstName != null && !firstName.isBlank())
            teacher.setFirstName(firstName);

        if (lastName != null && !lastName.isBlank())
            teacher.setLastName(lastName);

        if (courseType != null)
            teacher.setSpeciality(courseType);

        var updated = teacherRepository.save(teacher);

        return Result.success(new TeacherDto(updated));
    }

    public Result<Void> deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id))
            return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

        teacherRepository.deleteById(id);

        return Result.success();
    }
}
