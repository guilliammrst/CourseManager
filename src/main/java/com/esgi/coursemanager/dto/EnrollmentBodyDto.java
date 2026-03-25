package com.esgi.coursemanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentBodyDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    private String type;
}