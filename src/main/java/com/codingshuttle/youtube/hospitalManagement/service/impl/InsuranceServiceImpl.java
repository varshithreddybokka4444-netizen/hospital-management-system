package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.repository.InsuranceRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InsuranceServiceImpl {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;



    @Transactional
    public Patient assignInsurance(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId)
                        .orElseThrow(()->new EntityNotFoundException("Patient with id: "+patientId+" not found!!"));

        patient.setInsurance(insurance);

        insurance.setPatient(patient);//bidirectional consistency maintenance

        return patient;
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new EntityNotFoundException("Patient with id: "+patientId+" not found!!"));

        patient.setInsurance(null);

        return patient;
    }


}
