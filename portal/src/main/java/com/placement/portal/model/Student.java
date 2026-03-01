package com.placement.portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students") // Matches UML Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id") // Matches UML PK
    private Long id;

    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photograph_path")
    private String photographPath;

    @Column(name = "cgpa")
    private Double cgpa;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    // --- STRICT UML RELATIONSHIPS ---

    // FK1: Links to the 'domains' table
    // REMOVED cascade = CascadeType.ALL - Domain should be saved independently
    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    // FK2: Links to the 'specialisation' table
    // REMOVED cascade = CascadeType.ALL - Specialisation should be saved independently
    @ManyToOne
    @JoinColumn(name = "specialisation_id")
    private Specialisation specialisation;

    // FK3: Links to the 'placement' table
    @ManyToOne
    @JoinColumn(name = "placement_id")
    private Placement placement;

    // --- App Logic Fields (Required for Login/Security) ---
    @Column(nullable = false)
    private String password;

    private String role; // e.g. "STUDENT"
}