package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    //Optional<PatientResponseDto> registerNewPatient(PatientRequestDto);
    Optional<PatientResponseDto> getPatientById(Long id);


     PatientResponseDto registerNewPatient(PatientRequestDto addPatientRequestDto);


    List<PatientResponseDto> getAllPatients();


    void deletePatientById(Long id);

     PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto);
}
