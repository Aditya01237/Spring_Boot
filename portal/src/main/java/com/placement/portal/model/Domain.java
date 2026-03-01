package com.placement.portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "domain") // Matches UML
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domain_id")
    private Long id;

    @Column(name = "program")
    private String program; // e.g. "M.Tech"

    @Column(name = "batch")
    private String batch;   // e.g. "2025"

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "qualification")
    private String qualification; // e.g. "Bachelor of Technology"
}