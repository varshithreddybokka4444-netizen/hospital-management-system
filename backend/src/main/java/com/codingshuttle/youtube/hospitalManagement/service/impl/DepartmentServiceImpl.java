package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorSummaryDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.exception.ResourceNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.DepartmentRepository;
import com.codingshuttle.youtube.hospitalManagement.service.DepartmentService;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;
    private final DoctorService doctorService;

    @Override
    public DepartmentResponseDto createNewDepartment(DepartmentCreateDto addDepartmentRequestDto) {
        Department department = modelMapper.map(addDepartmentRequestDto,Department.class);

        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment,DepartmentResponseDto.class);
    }

    @Override
    public Department getDepartmentEntityByPublicId(String publicId){
        Department department = departmentRepository.findByPublicId(publicId).orElseThrow(()->
                new ResourceNotFoundException("Department not found with id "+publicId));


        return department;
    }

    public DepartmentResponseDto getDepartmentByPublicId(String publicId) {
        Department department = getDepartmentEntityByPublicId(publicId);

        return modelMapper.map(department,DepartmentResponseDto.class);
    }


    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department ->modelMapper.map(department,DepartmentResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public DepartmentResponseDto updateDepartment(String publicId, DepartmentUpdateDto updateDepartmentRequestDto) {

        Department department = getDepartmentEntityByPublicId(publicId);
        modelMapper.map(updateDepartmentRequestDto,department);

        return modelMapper.map(department,DepartmentResponseDto.class);
    }

    @Override
    @Transactional
    public DepartmentResponseDto updatePartialDepartment(String publicId, DepartmentUpdateDto partialUpdatepDepartmentRequestDto) {

        Department department = getDepartmentEntityByPublicId(publicId);
        modelMapper.map(partialUpdatepDepartmentRequestDto,department);

        return modelMapper.map(department,DepartmentResponseDto.class);
    }

    @Override
    @Transactional
    public DepartmentResponseDto changeHeadDoctor(String publicId, DepartmentUpdateDto addNewHeadDoctorRequestDto) {
        Department department = getDepartmentEntityByPublicId(publicId);

        Doctor doctor =
                doctorService.getDoctorEntityByPublicId(
                        addNewHeadDoctorRequestDto.getHeadDoctorPublicId()
                );

        department.setHeadDoctor(doctor);

        return modelMapper.map(department,DepartmentResponseDto.class);

    }

    @Override
    public void deleteDepartmentByPublicId(String publicId) {
        Department department = getDepartmentEntityByPublicId(publicId);
        departmentRepository.delete(department);
    }

    @Override
    public List<DoctorSummaryDto> getDoctorsByDepartment(String publicId) {
        Department department = getDepartmentEntityByPublicId(publicId);

        List<Doctor> doctors = new ArrayList<>(department.getDoctors());

        if(doctors.isEmpty()){
            throw new ResourceNotFoundException("No doctors found in department with publicId :"+publicId);
        }

        return doctors.stream()
                .map(doctor->modelMapper.map(doctor,DoctorSummaryDto.class))
                .toList();

    }


}
