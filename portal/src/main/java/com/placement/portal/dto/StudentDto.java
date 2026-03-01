package com.placement.portal.dto;

import lombok.Data;

@Data
public class StudentDto {
    private Long id;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String photographPath;
    private Double cgpa;
    private Integer totalCredits;
    private Integer graduationYear;
    private String program;
    private String specialisationCode;
    private String specialisationName;
    private Long placementId;
    private String role;
}

