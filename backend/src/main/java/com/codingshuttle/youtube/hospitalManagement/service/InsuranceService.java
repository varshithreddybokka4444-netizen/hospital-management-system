package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;

public interface InsuranceService {


    InsuranceResponseDto createInsurance(Patient patient, InsuranceCreateDto assignInsuranceRequestDto);
    InsuranceResponseDto removeInsurance(Patient patient);
    InsuranceResponseDto updateInsurance(Patient patient, InsuranceCreateDto updateInsuranceRequestDto);
    InsuranceResponseDto partialUpdateInsurance(Patient patient, InsuranceUpdateDto partilUpdateInsuranceRequestDto);

    InsuranceResponseDto getInsuranceOfPatient(Patient patient);

    Insurance getInsuranceEntityByPatient(Patient patient);
}
