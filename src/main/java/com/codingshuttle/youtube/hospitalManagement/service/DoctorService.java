package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface DoctorService{
    DoctorResponseDto getDoctorById(Long id);

    DoctorResponseDto registerNewDoctor(DoctorCreateDto doctor);

    List<DoctorResponseDto> getAllDoctors();

    void deleteDoctorById(Long id);


    DoctorResponseDto updateDoctor(Long id, DoctorCreateDto doctorUpdateRequest);

    DoctorResponseDto updatePartialDoctor(Long id, DoctorUpdateDto updatePartialDoctorUpdateDto);
}