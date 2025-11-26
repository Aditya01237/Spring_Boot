package com.placement.portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "placement_filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacementFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "placement_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion JSON loop
    private Placement placement;

    // STRICT UML: FK3 to Domains
    // CascadeType.ALL allows us to create a new Domain on the fly if it doesn't exist
    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    // STRICT UML: FK2 to Specialisation
    // CascadeType.ALL allows us to create a new Specialisation on the fly
    @ManyToOne
    @JoinColumn(name = "specialisation_id")
    private Specialisation specialisation;
}