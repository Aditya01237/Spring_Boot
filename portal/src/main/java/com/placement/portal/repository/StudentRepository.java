package com.placement.portal.repository;

import com.placement.portal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Make sure it extends JpaRepository<Student, Long> -> NOT <User, Long>
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Must return Optional<Student> -> NOT Optional<User>
    Optional<Student> findByEmail(String email);

    // Case-insensitive lookup to be more robust with OAuth providers
    Optional<Student> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);
}