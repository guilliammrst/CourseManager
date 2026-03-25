package com.esgi.coursemanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentQueryDto {

    private String email;

    private Integer page = 0;

    private Integer size = 10;
}