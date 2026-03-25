package com.esgi.coursemanager.dto;

import com.esgi.coursemanager.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentUpdateBodyDto {

    @NotNull
    private EnrollmentStatus status;
}
