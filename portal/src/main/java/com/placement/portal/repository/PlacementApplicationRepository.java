package com.placement.portal.repository;

import com.placement.portal.model.PlacementApplication;
import com.placement.portal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlacementApplicationRepository extends JpaRepository<PlacementApplication, Long> {

    // Fetch all applications submitted by a specific student
    List<PlacementApplication> findByStudent(Student student);

    // Check if a specific student has already applied for a specific placement
    // This prevents duplicate applications
    boolean existsByStudentAndPlacementId(Student student, Long placementId);
}