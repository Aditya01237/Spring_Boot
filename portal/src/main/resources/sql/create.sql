-- Schema creation script
-- Ensures tables exist before inserting seed data

CREATE TABLE IF NOT EXISTS domain (
    domain_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    program VARCHAR(100) NOT NULL UNIQUE,
    batch VARCHAR(50),
    capacity INT,
    qualification VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS specialisation (
    specialisation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150),
    description VARCHAR(255),
    year INT,
    credits_required INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS placement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organisation VARCHAR(150) NOT NULL,
    profile VARCHAR(150),
    description TEXT,
    intake INT,
    minimum_grade DOUBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(150),
    last_name VARCHAR(150),
    email VARCHAR(200) NOT NULL UNIQUE,
    photograph_path VARCHAR(255),
    cgpa DOUBLE,
    total_credits INT,
    graduation_year INT,
    domain_id BIGINT,
    specialisation_id BIGINT,
    placement_id BIGINT,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    CONSTRAINT fk_students_domain FOREIGN KEY (domain_id) REFERENCES domain(domain_id),
    CONSTRAINT fk_students_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisation(specialisation_id),
    CONSTRAINT fk_students_placement FOREIGN KEY (placement_id) REFERENCES placement(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS placement_filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placement_id BIGINT NOT NULL,
    domain_id BIGINT,
    specialisation_id BIGINT,
    CONSTRAINT fk_filter_placement FOREIGN KEY (placement_id) REFERENCES placement(id) ON DELETE CASCADE,
    CONSTRAINT fk_filter_domain FOREIGN KEY (domain_id) REFERENCES domain(domain_id),
    CONSTRAINT fk_filter_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisation(specialisation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS placement_student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placement_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    cv_application VARCHAR(255),
    about TEXT,
    acceptance BIT DEFAULT 0,
    comments VARCHAR(255),
    date DATE,
    CONSTRAINT fk_ps_placement FOREIGN KEY (placement_id) REFERENCES placement(id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_student FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

