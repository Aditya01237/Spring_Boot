package com.placement.portal.controller;

import com.placement.portal.model.Student;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    // UPDATED: Uses StudentRepository instead of UserRepository
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // UPDATED: Uses Student model
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Encode Password
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // Set Default Role
        if (student.getRole() == null) {
            student.setRole("STUDENT");
        }

        Student savedStudent = studentRepository.save(student);

        String token = jwtUtils.generateToken(savedStudent.getEmail(), savedStudent.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", savedStudent);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        Student student = studentRepository.findByEmail(email).orElseThrow();
        String token = jwtUtils.generateToken(student.getEmail(), student.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", student);
        return ResponseEntity.ok(response);
    }
}