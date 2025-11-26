package com.placement.portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specialisation") // Matches UML
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialisation_id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code; // e.g. "CS-AI"

    @Column(name = "name")
    private String name; // e.g. "Artificial Intelligence"

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private Integer year;

    @Column(name = "credits_required")
    private Integer creditsRequired;
}