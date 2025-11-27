-- Additional constraints & indexes separated for clarity

ALTER TABLE placement_filter
    ADD INDEX idx_filter_placement (placement_id),
    ADD INDEX idx_filter_domain (domain_id),
    ADD INDEX idx_filter_specialisation (specialisation_id);

ALTER TABLE placement_student
    ADD UNIQUE KEY uq_application (placement_id, student_id);

