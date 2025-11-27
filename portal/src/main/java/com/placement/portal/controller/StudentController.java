package com.placement.portal.controller;

import com.placement.portal.dto.StudentDto;
import com.placement.portal.mapper.StudentMapper;
import com.placement.portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/me")
    public ResponseEntity<StudentDto> getCurrentStudent(Authentication authentication) {
        String email = authentication.getName();

        return studentRepository.findByEmailIgnoreCase(email)
                .map(StudentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

