package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentSummaryDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.exception.ResourceNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.DepartmentRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));


        return convertToDoctorResponseDto(doctor);
    }

    @Override
    public Doctor getDoctorEntityByPublicId(String publicId) {


        return doctorRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with publicId " + publicId));
    }

    @Override
    public DoctorResponseDto getDoctorByPublicId(String publicId) {
        return convertToDoctorResponseDto(getDoctorEntityByPublicId(publicId));
    }


    @Override
    public DoctorResponseDto registerNewDoctor(DoctorCreateDto addDoctorRequest) {

        Department department = getDepartmentByPublicId(addDoctorRequest.getDepartmentPublicId());

        Doctor newDoctor = Doctor.builder()
                .name(addDoctorRequest.getName())
                .email(addDoctorRequest.getEmail())
                .specialisation(addDoctorRequest.getSpecialisation())
                .department(department)
                .build();

        department.getDoctors().add(newDoctor);

        Doctor savedDoctor = doctorRepository.save(newDoctor);

        return convertToDoctorResponseDto(savedDoctor);
    }

    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();


        return doctors.stream()
                .map(this::convertToDoctorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDoctorByPublicId(String publicId) {
        Doctor doctor = getDoctorEntityByPublicId(publicId);
        doctorRepository.delete(doctor);

    }

    @Override
    @Transactional
    public DoctorResponseDto updateDoctor(String publicId, DoctorCreateDto doctorUpdateRequest) {
        Doctor doctor = getDoctorEntityByPublicId(publicId);

        Department oldDepartment = doctor.getDepartment();

        oldDepartment.getDoctors().remove(doctor);

        Department newDepartment = getDepartmentByPublicId(doctorUpdateRequest.getDepartmentPublicId());

        newDepartment.getDoctors().add(doctor);

        doctor.setName(doctorUpdateRequest.getName());
        doctor.setEmail(doctorUpdateRequest.getEmail());
        doctor.setSpecialisation(doctorUpdateRequest.getSpecialisation());
        doctor.setDepartment(newDepartment);

        return convertToDoctorResponseDto(doctor);
    }

    @Override
    @Transactional
    public DoctorResponseDto updatePartialDoctor(String publicId, DoctorUpdateDto updatePartialDoctorUpdateDto) {
        Doctor doctor = getDoctorEntityByPublicId(publicId);
        Department oldDepartment = doctor.getDepartment();




        if (updatePartialDoctorUpdateDto.getDepartmentPublicId() != null) {

            Department newDepartment = getDepartmentByPublicId(updatePartialDoctorUpdateDto.getDepartmentPublicId());

            oldDepartment.getDoctors().remove(doctor);
            doctor.setDepartment(newDepartment);
            newDepartment.getDoctors().add(doctor);
        }


        if (updatePartialDoctorUpdateDto.getName() != null) {
            doctor.setName(updatePartialDoctorUpdateDto.getName());
        }

        if (updatePartialDoctorUpdateDto.getEmail() != null) {
            doctor.setEmail(updatePartialDoctorUpdateDto.getEmail());
        }

        if (updatePartialDoctorUpdateDto.getSpecialisation() != null) {
            doctor.setSpecialisation(updatePartialDoctorUpdateDto.getSpecialisation());
        }


        return convertToDoctorResponseDto(doctor);
    }

    private DoctorResponseDto convertToDoctorResponseDto(Doctor doctor) {

        DoctorResponseDto dto =
                modelMapper.map(doctor, DoctorResponseDto.class);

        dto.setDepartment(

                modelMapper.map(
                        doctor.getDepartment(),
                        DepartmentSummaryDto.class
                )
        );

        return dto;

    }

    private Department getDepartmentByPublicId(String publicId) {

        return departmentRepository.findByPublicId(publicId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Department not found with publicId " + publicId
                ));
    }
}

