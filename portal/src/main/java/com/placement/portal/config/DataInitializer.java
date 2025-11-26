package com.placement.portal.config;

import com.placement.portal.model.*;
import com.placement.portal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner loadData(StudentRepository studentRepository,
                                      PlacementRepository placementRepository,
                                      DomainRepository domainRepository,
                                      SpecialisationRepository specialisationRepository) {
        return args -> {
            System.out.println("🚀 Starting Data Initialization...");

            // 1. Define Domains & Specialisations (Check if exist first)
            Domain mTech = domainRepository.findByProgram("M.Tech").orElseGet(() -> {
                Domain d = new Domain(null, "M.Tech", "2025", 150, "B.Tech");
                return domainRepository.save(d);
            });

            Domain imTech = domainRepository.findByProgram("iM.Tech").orElseGet(() -> {
                Domain d = new Domain(null, "iM.Tech", "2025", 120, "12th Grade");
                return domainRepository.save(d);
            });

            Specialisation cse = specialisationRepository.findByCode("CSE").orElseGet(() ->
                    specialisationRepository.save(new Specialisation(null, "CSE", "Computer Science", "Core", 2025, 20)));

            Specialisation dsai = specialisationRepository.findByCode("DSAI").orElseGet(() ->
                    specialisationRepository.save(new Specialisation(null, "DSAI", "Data Science AI", "AI", 2025, 20)));

            Specialisation ece = specialisationRepository.findByCode("ECE").orElseGet(() ->
                    specialisationRepository.save(new Specialisation(null, "ECE", "Electronics", "Hardware", 2025, 20)));

            // 2. Create Students
            if (studentRepository.count() == 0) {
                // UPDATED: Using @gmail.com
                createStudent("mt_cse_1", "Amit", "Sharma", mTech, cse, 8.8, studentRepository);
                createStudent("mt_cse_2", "Priya", "Singh", mTech, cse, 8.2, studentRepository);
                createStudent("mt_dsai_1", "Rohan", "Verma", mTech, dsai, 9.1, studentRepository);

                // --- IMPORTANT FOR TESTING ---
                // Add your REAL Google email here so you can actually log in!
                // Replace "your.email" with your actual Gmail address.
                createRealStudent("adityapareek874", "Aditya", "Pareek", mTech, cse, 9.5, studentRepository);

                System.out.println("✅ Students Created with @gmail.com");
            }

            // 3. Create Companies (Only if empty)
            if (placementRepository.count() == 0) {
                createPlacement("Google", "SDE-III", "Backend Systems", 5, 8.5, List.of(
                        createFilter(mTech, cse), createFilter(imTech, cse), createFilter(mTech, dsai)
                ), placementRepository);

                createPlacement("Microsoft", "Software Engineer", "Full Stack", 10, 7.5, List.of(
                        createFilter(mTech, cse), createFilter(imTech, cse)
                ), placementRepository);

                // ... Add other companies similarly
                System.out.println("✅ Dummy Companies Created");
            }
        };
    }

    // Helper for Dummy Data
    private void createStudent(String usernamePrefix, String firstName, String lastName, Domain domain, Specialisation spec, double cgpa,
                               StudentRepository repo) {
        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        // CHANGED: Now uses gmail.com
        s.setEmail(usernamePrefix + "@gmail.com");
        s.setPassword(this.passwordEncoder.encode("password"));
        s.setRollNumber("RN_" + usernamePrefix.toUpperCase());
        s.setCgpa(cgpa);
        s.setGraduationYear(2025);
        s.setRole("STUDENT");
        s.setDomain(domain);
        s.setSpecialisation(spec);
        repo.save(s);
    }

    // Helper for YOUR Real Account
    private void createRealStudent(String emailPrefix, String firstName, String lastName, Domain domain, Specialisation spec, double cgpa,
                                   StudentRepository repo) {
        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setEmail(emailPrefix + "@gmail.com"); // Ensures exact match with your Google Auth
        s.setPassword(this.passwordEncoder.encode("password"));
        s.setRollNumber("MY_REAL_ACCOUNT");
        s.setCgpa(cgpa);
        s.setGraduationYear(2025);
        s.setRole("STUDENT");
        s.setDomain(domain);
        s.setSpecialisation(spec);
        repo.save(s);
    }

    private PlacementFilter createFilter(Domain d, Specialisation s) {
        PlacementFilter f = new PlacementFilter();
        f.setDomain(d);
        f.setSpecialisation(s);
        return f;
    }

    private void createPlacement(String org, String profile, String desc, int intake, double minGrade,
                                 List<PlacementFilter> filters, PlacementRepository repo) {
        Placement p = new Placement();
        p.setOrganisation(org);
        p.setProfile(profile);
        p.setDescription(desc);
        p.setIntake(intake);
        p.setMinimumGrade(minGrade);
        for(PlacementFilter f : filters) {
            f.setPlacement(p);
        }
        p.setFilters(filters);
        repo.save(p);
    }
}