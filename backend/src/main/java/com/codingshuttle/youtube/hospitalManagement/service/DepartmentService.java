package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorSummaryDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
     DepartmentResponseDto createNewDepartment( DepartmentCreateDto addDepartmentRequestDto);

    DepartmentResponseDto getDepartmentByPublicId(String publicId);
    Department getDepartmentEntityByPublicId(String publicId);
    List<DepartmentResponseDto> getAllDepartments();

     DepartmentResponseDto updateDepartment(String publicId, DepartmentUpdateDto updateDepartmentRequestDto);


     DepartmentResponseDto updatePartialDepartment(String publicId, DepartmentUpdateDto partialUpdatepDepartmentRequestDto);

     DepartmentResponseDto changeHeadDoctor(String publicId, DepartmentUpdateDto addNewHeadDoctorRequest);

    void deleteDepartmentByPublicId(String publicId);

    List<DoctorSummaryDto> getDoctorsByDepartment(String departmentPublicId);
}
