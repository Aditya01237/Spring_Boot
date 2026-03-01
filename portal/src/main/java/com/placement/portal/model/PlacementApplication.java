package com.placement.portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "placement_student") // Matches UML Table Name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacementApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "placement_id", nullable = false)
    private Placement placement;

    // Linked to the STUDENT entity (not User)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "cv_application")
    private String cvFilePath; // Path to stored file

    private String about;

    private boolean acceptance; // Selected or not

    private String comments;

    private LocalDate date;
}