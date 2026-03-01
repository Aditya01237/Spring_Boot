SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE placement_student;
TRUNCATE TABLE placement_filter;
TRUNCATE TABLE students;
TRUNCATE TABLE placement;
TRUNCATE TABLE specialisation;
TRUNCATE TABLE domain;
SET FOREIGN_KEY_CHECKS=1;

-- Seed Domains
INSERT INTO domain (program, batch, capacity, qualification) VALUES
('M.Tech', '2025', 150, 'B.Tech'),
('iM.Tech', '2025', 120, '12th Grade');

-- Seed Specialisations
INSERT INTO specialisation (code, name, description, year, credits_required) VALUES
('CSE', 'Computer Science', 'Core', 2025, 20),
('DSAI', 'Data Science AI', 'AI', 2025, 20),
('ECE', 'Electronics', 'Hardware', 2025, 20);

-- Seed Placements
INSERT INTO placement (organisation, profile, description, intake, minimum_grade) VALUES
('Google', 'SDE-III', 'Backend Systems', 5, 8.5),
('Microsoft', 'Software Engineer', 'Full Stack', 10, 7.5),
('Amazon', 'SDE-II', 'Distributed Systems', 8, 8.0),
('Apple', 'iOS Engineer', 'Mobile Development', 6, 7.8),
('Meta', 'Product Engineer', 'Social Platform Features', 7, 8.2),
('Netflix', 'Backend Engineer', 'Streaming Platform', 4, 8.7),
('Uber', 'Backend Engineer', 'Realtime Routing Systems', 9, 7.5),
('Airbnb', 'Full Stack Engineer', 'Travel & Experiences', 5, 7.2),
('Adobe', 'Software Engineer', 'Creative Cloud Products', 6, 7.0),
('Salesforce', 'Platform Developer', 'CRM Platform', 10, 7.3),
('SAP', 'Application Developer', 'Enterprise Solutions', 12, 7.0),
('Oracle', 'Cloud Engineer', 'Database & Cloud Services', 8, 7.6),
('Cisco', 'Network Engineer', 'Networking & Security', 6, 7.4),
('Intel', 'Hardware / Software Engineer', 'Processor & Tooling', 5, 7.8);

-- Helper variables
SET @pwd = '$2a$10$8JC.Z8c4/nAxt..YXPw4feSQM.og5AsV0vdLrbGCJ3jrw2FQX72au';

-- Seed Students
INSERT INTO students (roll_number, first_name, last_name, email, cgpa, graduation_year, domain_id, specialisation_id, password, role)
VALUES
('RN_MT_CSE_1', 'Amit', 'Sharma', 'mt_cse_1@gmail.com', 8.8, 2025,
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE'),
    @pwd, 'STUDENT'),
('RN_MT_CSE_2', 'Priya', 'Singh', 'mt_cse_2@gmail.com', 8.2, 2025,
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE'),
    @pwd, 'STUDENT'),
('RN_MT_DSAI_1', 'Rohan', 'Verma', 'mt_dsai_1@gmail.com', 9.1, 2025,
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI'),
    @pwd, 'STUDENT'),
('MY_REAL_ACCOUNT', 'Aditya', 'Pareek', 'adityapareek874@gmail.com', 9.5, 2025,
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE'),
    @pwd, 'STUDENT');

-- Seed Placement Filters (a subset representative of eligibility rules)
INSERT INTO placement_filter (placement_id, domain_id, specialisation_id)
VALUES
((SELECT id FROM placement WHERE organisation='Google'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Google'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Google'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Google'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Microsoft'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Microsoft'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Microsoft'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Amazon'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Amazon'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Apple'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Apple'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Meta'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Meta'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Netflix'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Netflix'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='DSAI')),
((SELECT id FROM placement WHERE organisation='Uber'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Uber'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Airbnb'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Airbnb'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Adobe'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Adobe'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Salesforce'),
    (SELECT domain_id FROM domain WHERE program='M.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE')),
((SELECT id FROM placement WHERE organisation='Salesforce'),
    (SELECT domain_id FROM domain WHERE program='iM.Tech'),
    (SELECT specialisation_id FROM specialisation WHERE code='CSE'));

