package com.placement.portal.mapper;

import com.placement.portal.dto.PlacementApplicationDto;
import com.placement.portal.model.PlacementApplication;

public class PlacementApplicationMapper {

    private PlacementApplicationMapper() {
    }

    public static PlacementApplicationDto toDto(PlacementApplication application) {
        if (application == null) {
            return null;
        }
        PlacementApplicationDto dto = new PlacementApplicationDto();
        dto.setId(application.getId());
        dto.setPlacement(PlacementMapper.toDto(application.getPlacement()));
        dto.setCvFilePath(application.getCvFilePath());
        dto.setAbout(application.getAbout());
        dto.setAcceptance(application.isAcceptance());
        dto.setComments(application.getComments());
        dto.setDate(application.getDate());
        return dto;
    }
}

