package com.placement.portal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlacementDto {
    private Long id;
    private String organisation;
    private String profile;
    private String description;
    private Integer intake;
    private Double minimumGrade;
    private List<PlacementFilterDto> filters;
}

