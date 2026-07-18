package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.*;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;

import java.util.List;

public interface PatientService {
    //Optional<PatientResponseDto> registerNewPatient(PatientRequestDto);
    PatientResponseDto getPatientById(Long patientId);

    PatientResponseDto getPatientByPublicId(String patientPublicId);
    Patient getPatientEntityByPublicId(String patientPublicId);
     PatientResponseDto registerNewPatient(PatientCreateDto addPatientRequestDto);


    List<PatientResponseDto> getAllPatients();


    void deletePatientByPublicId(String patientPublicId);

    //void deletepatientPatientById(Long id);

     PatientResponseDto updatePatient(String patientPublicId, PatientCreateDto patientRequestDto);

     PatientResponseDto updatePartialPatient(String patientPublicId, PatientUpdateDto updatePartialPatientRequestDto);

    InsuranceResponseDto assignInsurance(String patientPublicId, InsuranceCreateDto assignInsuranceRequestDto);
    InsuranceResponseDto dissociateInsurance(String patientPublicId);
    InsuranceResponseDto updateInsurance(String patientPublicId, InsuranceCreateDto updateInsuranceRequestDto);
    InsuranceResponseDto partialUpdateInsurance(String patientPublicId, InsuranceUpdateDto partilUpdateInsuranceRequestDto);
    InsuranceResponseDto getInsuranceOfPatient(String patientPublicId);
    Insurance getInsuranceEntityByPatient(String patientPublicId);
}
