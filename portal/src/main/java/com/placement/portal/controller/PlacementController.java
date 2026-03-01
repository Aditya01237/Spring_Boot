package com.placement.portal.controller;

import com.placement.portal.dto.PlacementDto;
import com.placement.portal.mapper.PlacementMapper;
import com.placement.portal.model.*;
import com.placement.portal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/placements")
public class PlacementController {

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private SpecialisationRepository specialisationRepository;

    // 1. Create Placement (Updated to prevent duplicates)
    @PostMapping("/create")
    public PlacementDto createPlacement(@RequestBody Placement placement) {
        if (placement.getFilters() != null) {
            for (PlacementFilter filter : placement.getFilters()) {
                // 1. Link the filter to the parent Placement
                filter.setPlacement(placement);

                // 2. Handle Domain: Check if it already exists in DB to reuse it
                if (filter.getDomain() != null) {
                    Optional<Domain> existingDomain = domainRepository.findByProgram(filter.getDomain().getProgram());
                    if (existingDomain.isPresent()) {
                        // If found, attach the EXISTING database entity (with ID)
                        filter.setDomain(existingDomain.get());
                    }
                    // If not found, Hibernate will create a new one automatically (CascadeType.ALL)
                }

                // 3. Handle Specialisation: Check if it already exists in DB to reuse it
                if (filter.getSpecialisation() != null) {
                    Optional<Specialisation> existingSpec = specialisationRepository.findByCode(filter.getSpecialisation().getCode());
                    if (existingSpec.isPresent()) {
                        // If found, attach the EXISTING database entity (with ID)
                        filter.setSpecialisation(existingSpec.get());
                    }
                    // If not found, Hibernate will create a new one automatically
                }
            }
        }
        Placement saved = placementRepository.save(placement);
        return PlacementMapper.toDto(saved);
    }

    // 2. Get All Placements
    @GetMapping("/all")
    public List<PlacementDto> getAllPlacements() {
        return placementRepository.findAll().stream()
                .map(PlacementMapper::toDto)
                .collect(Collectors.toList());
    }

    // 2b. Get Placement by ID (for detailed view)
    @GetMapping("/{id}")
    public ResponseEntity<PlacementDto> getPlacementById(@PathVariable Long id) {
        return placementRepository.findById(id)
                .map(PlacementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get Eligible Placements
    @GetMapping("/eligible")
    public ResponseEntity<List<PlacementDto>> getEligiblePlacements() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student student = studentRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Student not found for email: " + email));

        // Extract deep properties (Program Name & Specialisation Code)
        String domainProgram = (student.getDomain() != null) ? student.getDomain().getProgram() : "";
        String specCode = (student.getSpecialisation() != null) ? student.getSpecialisation().getCode() : "";

        List<PlacementDto> list = placementRepository.findEligiblePlacements(
                student.getCgpa(),
                domainProgram,
                specCode
        ).stream().map(PlacementMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}