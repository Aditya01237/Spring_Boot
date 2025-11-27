package com.placement.portal.controller;

import com.placement.portal.dto.PlacementApplicationDto;
import com.placement.portal.mapper.PlacementApplicationMapper;
import com.placement.portal.model.Placement;
import com.placement.portal.model.PlacementApplication;
import com.placement.portal.model.Student;
import com.placement.portal.repository.PlacementApplicationRepository;
import com.placement.portal.repository.PlacementRepository;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private PlacementApplicationRepository applicationRepository;

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // 1. Apply for a Placement (Updated for File Upload)
    // Consumes 'multipart/form-data' to handle the PDF file + text data
    @PostMapping(value = "/apply/{placementId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyForPlacement(
            @PathVariable Long placementId,
            @RequestParam("about") String about,
            @RequestParam("cvFile") MultipartFile cvFile
    ) {
        // Get logged-in student from Security Context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if already applied
        if (applicationRepository.existsByStudentAndPlacementId(student, placementId)) {
            return ResponseEntity.badRequest().body("You have already applied for this placement.");
        }

        // Get the Placement details
        Placement placement = placementRepository.findById(placementId)
                .orElseThrow(() -> new RuntimeException("Placement not found"));

        // Store the file physically using our Service
        // This returns the relative path (e.g., "uploads/cvs/RN_123_Google.pdf")
        String savedFilePath = fileStorageService.store(cvFile, student.getRollNumber());

        // Create the Application record
        PlacementApplication app = new PlacementApplication();
        app.setStudent(student);
        app.setPlacement(placement);
        app.setDate(LocalDate.now());
        app.setAbout(about);
        app.setAcceptance(false); // Default status

        // Save the path we got from the service into the database
        app.setCvFilePath(savedFilePath);

        applicationRepository.save(app);

        return ResponseEntity.ok("Application and CV submitted successfully!");
    }

    // 2. See My Applications History
    @GetMapping("/my-applications")
    public List<PlacementApplicationDto> getMyApplications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Student student = studentRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Student not found for email: " + email));
        return applicationRepository.findByStudent(student).stream()
                .map(PlacementApplicationMapper::toDto)
                .collect(Collectors.toList());
    }

    // 3. Download CV Endpoint
    @GetMapping("/download/{filename}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadCv(@PathVariable String filename) {
        try {
            // Locate the file
            java.nio.file.Path filePath = java.nio.file.Paths.get("uploads/cvs/").resolve(filename).normalize();
            org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}