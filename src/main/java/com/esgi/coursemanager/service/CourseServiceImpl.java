package com.esgi.coursemanager.service;

import com.esgi.coursemanager.common.Result;
import com.esgi.coursemanager.dto.CourseDto;
import com.esgi.coursemanager.dto.CourseQueryDto;
import com.esgi.coursemanager.model.Course;
import com.esgi.coursemanager.model.CourseType;
import com.esgi.coursemanager.model.Teacher;
import com.esgi.coursemanager.repository.CourseRepository;
import com.esgi.coursemanager.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public Result<CourseDto> getCourse(Long id) {
        var courseOpt = courseRepository.findById(id);
        if (courseOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Course not found.");

        var course = courseOpt.get();
        var courseDto = new CourseDto(course);

        return Result.success(courseDto);
    }

    public Result<List<CourseDto>> getCourses(CourseQueryDto queryDto) {
        Pageable pageable = PageRequest.of(
                queryDto.getPage(),
                queryDto.getSize()
        );

        Page<Course> courses;

        if (queryDto.getCourseType() != null)
            courses = courseRepository.findByCourseType(queryDto.getCourseType(), pageable);
        else
            courses = courseRepository.findAll(pageable);

        var coursesDto = courses.stream()
                .map(CourseDto::new)
                .toList();

        return Result.success(coursesDto);
    }

    public Result<Long> createCourse(String title, String description, CourseType courseType, Long teacherId) {
        Teacher teacher = null;

        if (teacherId != null) {
            var teacherOpt = teacherRepository.findById(teacherId);

            if (teacherOpt.isEmpty())
                return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

            teacher = teacherOpt.get();
        }

        var course = new Course(title, description, courseType, teacher);

        var createdCourse = courseRepository.save(course);

        return Result.success(createdCourse.getId());
    }

    public Result<CourseDto> updateCourse(Long id, String title, String description, CourseType courseType, Long teacherId) {
        var courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Course not found");

        var course = courseOpt.get();

        if (title != null && !title.isBlank())
            course.setTitle(title);

        if (description != null && !description.isBlank())
            course.setDescription(description);

        if (courseType != null)
            course.setCourseType(courseType);

        if (teacherId != null) {
            if (teacherId == 0)
                course.setTeacher(null);
            else {
                var teacherOpt = teacherRepository.findById(teacherId);

                if (teacherOpt.isEmpty())
                    return Result.failure(HttpStatus.NOT_FOUND, "Teacher not found");

                course.setTeacher(teacherOpt.get());
            }
        }

        var updatedCourse = courseRepository.save(course);
        var courseDto = new CourseDto(updatedCourse);

        return Result.success(courseDto);
    }

    public Result<Void> deleteCourse(Long id) {
        var courseOpt = courseRepository.findById(id);
        if (courseOpt.isEmpty())
            return Result.failure(HttpStatus.NOT_FOUND, "Course not found.");

        var course = courseOpt.get();
        courseRepository.delete(course);

        return Result.success();
    }
}
