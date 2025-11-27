package com.placement.portal.mapper;

import com.placement.portal.dto.StudentDto;
import com.placement.portal.model.Student;

public class StudentMapper {

    private StudentMapper() {
    }

    public static StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setRollNumber(student.getRollNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhotographPath(student.getPhotographPath());
        dto.setCgpa(student.getCgpa());
        dto.setTotalCredits(student.getTotalCredits());
        dto.setGraduationYear(student.getGraduationYear());
        dto.setRole(student.getRole());
        if (student.getDomain() != null) {
            dto.setProgram(student.getDomain().getProgram());
        }
        if (student.getSpecialisation() != null) {
            dto.setSpecialisationCode(student.getSpecialisation().getCode());
            dto.setSpecialisationName(student.getSpecialisation().getName());
        }
        if (student.getPlacement() != null) {
            dto.setPlacementId(student.getPlacement().getId());
        }
        return dto;
    }
}

