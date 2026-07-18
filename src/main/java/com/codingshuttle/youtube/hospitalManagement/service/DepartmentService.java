package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
     DepartmentResponseDto createNewDepartment( DepartmentCreateDto addDepartmentRequestDto);

    DepartmentResponseDto getDepartmentByPublicId(String publicId);

    List<DepartmentResponseDto> getAllDepartments();

     DepartmentResponseDto updateDepartment(String publicId, DepartmentUpdateDto updateDepartmentRequestDto);


     DepartmentResponseDto updatePartialDepartment(String publicId, DepartmentUpdateDto partialUpdatepDepartmentRequestDto);

     DepartmentResponseDto changeHeadDoctor(String publicId, DepartmentUpdateDto addNewHeadDoctorRequest);
}
