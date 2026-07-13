package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface DoctorService{
    DoctorResponseDto getDoctorById(Long id);

    DoctorResponseDto getDoctorByPublicId(String publicId);
    Doctor getDoctorEntityByPublicId(String publicId);
    DoctorResponseDto registerNewDoctor(DoctorCreateDto doctor);

    List<DoctorResponseDto> getAllDoctors();

    void deleteDoctorByPublicId(String publicId);


    DoctorResponseDto updateDoctor(String publicId, DoctorCreateDto doctorUpdateRequest);

    DoctorResponseDto updatePartialDoctor(String publicId, DoctorUpdateDto updatePartialDoctorUpdateDto);


}