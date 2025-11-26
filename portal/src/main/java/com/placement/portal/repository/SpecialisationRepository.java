package com.placement.portal.repository;

import com.placement.portal.model.Specialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpecialisationRepository extends JpaRepository<Specialisation, Long> {
    Optional<Specialisation> findByCode(String code);
}