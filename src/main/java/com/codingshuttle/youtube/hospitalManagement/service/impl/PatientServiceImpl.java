package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.*;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourseNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import com.codingshuttle.youtube.hospitalManagement.service.InsuranceService;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final InsuranceService insuranceService;

    @Transactional(readOnly = true)
    @Override
    public PatientResponseDto getPatientById(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new ResourseNotFoundException("Patient not found with Id "+patientId));
        return modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    public PatientResponseDto getPatientByPublicId(String patientPublicId) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);

        return modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    public Patient getPatientEntityByPublicId(String patientPublicId) {
        Patient patient = patientRepository.findByPublicId(patientPublicId)
                .orElseThrow(()->new ResourseNotFoundException("Patient not found with publicId "+patientPublicId));
        return patient;
    }

//    public void deleteById(long id) {
//        patientRepository.deleteById(id);
//    }

    @Override
    public PatientResponseDto registerNewPatient(@Valid PatientCreateDto addPatientRequestDto) {

        Patient newPatient = modelMapper.map(addPatientRequestDto,Patient.class);
        Patient savedPatient = patientRepository.save(newPatient);

      return modelMapper.map(savedPatient, PatientResponseDto.class);
  }

    @Override
    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if(patients.isEmpty()){
            throw new ResourseNotFoundException("No patients found");
        }
        return patients.stream()
                .map(patient->modelMapper.map(patient,PatientResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePatientByPublicId(String patientPublicId) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        patientRepository.delete(patient);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(String patientPublicId, PatientCreateDto updatePatientRequestDto) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        modelMapper.map( updatePatientRequestDto,patient);

        return modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePartialPatient(String patientPublicId, PatientUpdateDto updatePartialPatientRequestDto) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        modelMapper.map( updatePartialPatientRequestDto,patient);

        return modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    public InsuranceResponseDto assignInsurance(String patientPublicId, InsuranceCreateDto assignInsuranceRequestDto) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        return insuranceService.createInsurance(patient,assignInsuranceRequestDto);
    }

    @Override
    public InsuranceResponseDto dissociateInsurance(String patientPublicId) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        return insuranceService.removeInsurance(patient);
    }

    @Override
    public InsuranceResponseDto updateInsurance(String patientPublicId, InsuranceCreateDto updateInsuranceRequestDto) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        return insuranceService.updateInsurance(patient,updateInsuranceRequestDto);

    }

    @Override
    public InsuranceResponseDto partialUpdateInsurance(String patientPublicId, InsuranceUpdateDto partilUpdateInsuranceRequestDto){
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        return insuranceService.partialUpdateInsurance(patient,partilUpdateInsuranceRequestDto);
    }

    @Override
    public InsuranceResponseDto getInsuranceOfPatient(String patientPublicId) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);
        return insuranceService.getInsuranceOfPatient(patient);
    }

    @Override
    public Insurance getInsuranceEntityByPatient(String patientPublicId) {
        Patient patient = getPatientEntityByPublicId(patientPublicId);

        return insuranceService.getInsuranceEntityByPatient(patient);
    }
}
