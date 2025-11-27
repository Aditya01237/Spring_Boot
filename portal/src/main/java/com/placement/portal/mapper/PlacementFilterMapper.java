package com.placement.portal.mapper;

import com.placement.portal.dto.PlacementFilterDto;
import com.placement.portal.model.PlacementFilter;

public class PlacementFilterMapper {

    private PlacementFilterMapper() {
    }

    public static PlacementFilterDto toDto(PlacementFilter filter) {
        if (filter == null) {
            return null;
        }
        PlacementFilterDto dto = new PlacementFilterDto();
        if (filter.getDomain() != null) {
            dto.setDomainProgram(filter.getDomain().getProgram());
        }
        if (filter.getSpecialisation() != null) {
            dto.setSpecialisationCode(filter.getSpecialisation().getCode());
            dto.setSpecialisationName(filter.getSpecialisation().getName());
        }
        return dto;
    }
}

