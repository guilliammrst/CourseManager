package com.esgi.coursemanager.dto;

import jakarta.validation.constraints.NotNull;

public class EnrollmentBodyDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    @NotNull
    private String type;

    public EnrollmentBodyDto() {}

    public EnrollmentBodyDto(Long studentId, Long courseId, String type) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.type = type;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}