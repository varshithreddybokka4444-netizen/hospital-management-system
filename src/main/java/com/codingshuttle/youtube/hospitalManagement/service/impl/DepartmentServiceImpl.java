package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourseNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.DepartmentRepository;
import com.codingshuttle.youtube.hospitalManagement.service.DepartmentService;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;
    private final DoctorService doctorService;

    @Override
    public DepartmentResponseDto createNewDepartment(DepartmentCreateDto addDepartmentRequestDto) {
        Department department = modelMapper.map(addDepartmentRequestDto,Department.class);
        Doctor doctor = modelMapper.map(doctorService.getDoctorByPublicId(addDepartmentRequestDto.getHeadDoctorPublicId()), Doctor.class);
        department.getDoctors().add(doctor);


        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment,DepartmentResponseDto.class);
    }

    public DepartmentResponseDto getDepartmentByPublicId(String publicId) {
        Department department = departmentRepository.findByPublicId(publicId).orElseThrow(()->
                new ResourseNotFoundException("Department not found with id "+publicId));

        Doctor doctor = doctorService.getDoctorEntityByPublicId(publicId);

        department.getDoctors().add(doctor);

        return modelMapper.map(department,DepartmentResponseDto.class);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        if(departments.isEmpty()){
            throw new ResourseNotFoundException("No departments found");
        }

        return departments.stream()
                .map(department->modelMapper.map(department,DepartmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentResponseDto updateDepartment(String publicId, DepartmentUpdateDto updateDepartmentRequestDto) {

        Department department = departmentRepository.findByPublicId(publicId).orElseThrow(()->
                new ResourseNotFoundException("Department not found with id "+publicId));
        Doctor doctor = modelMapper.map
                (doctorService.getDoctorByPublicId(updateDepartmentRequestDto.getHeadDoctorPublicId()),Doctor.class);
        department.getDoctors().add(doctor);


        modelMapper.map(department,updateDepartmentRequestDto);

        return modelMapper.map(department,DepartmentResponseDto.class);
    }

    @Override
    @Transactional
    public DepartmentResponseDto updatePartialDepartment(String publicId, DepartmentUpdateDto partialUpdatepDepartmentRequestDto) {

        Department department = departmentRepository.findByPublicId(publicId).orElseThrow(()->
                new ResourseNotFoundException("Department not found with publicId "+publicId));
        modelMapper.map(department,partialUpdatepDepartmentRequestDto);

        if(partialUpdatepDepartmentRequestDto.getHeadDoctorPublicId()!=null){
            Doctor doctor = modelMapper.map
                    (doctorService.getDoctorByPublicId(partialUpdatepDepartmentRequestDto.getHeadDoctorPublicId()),Doctor.class);
            department.getDoctors().add(doctor);
        }

        return modelMapper.map(department,DepartmentResponseDto.class);
    }

    @Override
    @Transactional
    public DepartmentResponseDto changeHeadDoctor(String publicId, DepartmentUpdateDto addNewHeadDoctorRequestDto) {
        Department department = departmentRepository.findByPublicId(publicId).orElseThrow(()->
                new ResourseNotFoundException("Department not found with publicId "+publicId));
        Doctor doctor = modelMapper.map(doctorService.getDoctorByPublicId(addNewHeadDoctorRequestDto.getHeadDoctorPublicId()),Doctor.class);
        department.getDoctors().add(doctor);

        return modelMapper.map(department,DepartmentResponseDto.class);

    }



}
