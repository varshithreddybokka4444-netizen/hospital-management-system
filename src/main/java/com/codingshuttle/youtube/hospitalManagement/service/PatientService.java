package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientUpdateDto;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    //Optional<PatientResponseDto> registerNewPatient(PatientRequestDto);
    PatientResponseDto getPatientById(Long id);


     PatientResponseDto registerNewPatient(PatientCreateDto addPatientRequestDto);


    List<PatientResponseDto> getAllPatients();


    void deletePatientById(Long id);

     PatientResponseDto updatePatient(Long id, PatientCreateDto patientRequestDto);

     PatientResponseDto updatePartialPatient(Long id, PatientUpdateDto updatePartialPatientRequestDto);
}
