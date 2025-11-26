package com.placement.portal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "placement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organisation") // Matches UML column name
    private String organisation;

    private String profile;

    private String description;

    private Integer intake;

    @Column(name = "minimum_grade")
    private Double minimumGrade;

    // Relationship to the separate Placement_Filter table
    // mappedBy = "placement" refers to the field in the PlacementFilter class
    @OneToMany(mappedBy = "placement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Prevents infinite recursion when converting to JSON
    private List<PlacementFilter> filters;
}