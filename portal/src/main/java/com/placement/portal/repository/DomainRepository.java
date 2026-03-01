package com.placement.portal.repository;

import com.placement.portal.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByProgram(String program);
}