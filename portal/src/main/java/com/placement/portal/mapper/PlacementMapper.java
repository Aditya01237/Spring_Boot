package com.placement.portal.mapper;

import com.placement.portal.dto.PlacementDto;
import com.placement.portal.model.Placement;

import java.util.Collections;
import java.util.stream.Collectors;

public class PlacementMapper {

    private PlacementMapper() {
    }

    public static PlacementDto toDto(Placement placement) {
        if (placement == null) {
            return null;
        }
        PlacementDto dto = new PlacementDto();
        dto.setId(placement.getId());
        dto.setOrganisation(placement.getOrganisation());
        dto.setProfile(placement.getProfile());
        dto.setDescription(placement.getDescription());
        dto.setIntake(placement.getIntake());
        dto.setMinimumGrade(placement.getMinimumGrade());
        if (placement.getFilters() != null) {
            dto.setFilters(
                    placement.getFilters().stream()
                            .map(PlacementFilterMapper::toDto)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setFilters(Collections.emptyList());
        }
        return dto;
    }
}

