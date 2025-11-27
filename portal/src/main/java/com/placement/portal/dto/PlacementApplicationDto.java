package com.placement.portal.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlacementApplicationDto {
    private Long id;
    private PlacementDto placement;
    private String cvFilePath;
    private String about;
    private boolean acceptance;
    private String comments;
    private LocalDate date;
}

