package com.placement.portal.repository;

import com.placement.portal.model.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacementRepository extends JpaRepository<Placement, Long> {

    // STRICT UML QUERY:
    // We match the Student's Domain Program & Specialisation Code with the Filter's
    // This traverses the relationships: Placement -> PlacementFilter -> Domain / Specialisation
    @Query("SELECT DISTINCT p FROM Placement p " +
            "JOIN p.filters f " +
            "WHERE p.minimumGrade <= :cgpa " +
            "AND f.domain.program = :domainProgram " +
            "AND (f.specialisation IS NULL OR f.specialisation.code = :specCode)")
    List<Placement> findEligiblePlacements(@Param("cgpa") Double cgpa,
                                           @Param("domainProgram") String domainProgram,
                                           @Param("specCode") String specCode);
}