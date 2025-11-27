package com.placement.portal.controller;

import com.placement.portal.model.Student;
import com.placement.portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentStudent(Authentication authentication) {
        String email = authentication.getName();

        return studentRepository.findByEmailIgnoreCase(email)
                .map(student -> {
                    Map<String, Object> dto = new HashMap<>();
                    dto.put("firstName", student.getFirstName());
                    dto.put("lastName", student.getLastName());
                    dto.put("email", student.getEmail());
                    dto.put("rollNumber", student.getRollNumber());
                    dto.put("cgpa", student.getCgpa());
                    dto.put("graduationYear", student.getGraduationYear());
                    dto.put("role", student.getRole());
                    if (student.getDomain() != null) {
                        dto.put("program", student.getDomain().getProgram());
                    }
                    if (student.getSpecialisation() != null) {
                        dto.put("specialisationCode", student.getSpecialisation().getCode());
                        dto.put("specialisationName", student.getSpecialisation().getName());
                    }
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

