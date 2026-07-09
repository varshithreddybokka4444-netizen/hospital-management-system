package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourseNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
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
    public final ModelMapper modelMapper;
    @Transactional(readOnly = true)
    @Override
    public PatientResponseDto getPatientById(Long id){
        Patient p1 =   patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));

        return modelMapper.map(p1,PatientResponseDto.class);
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
    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));
        patientRepository.delete(patient);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(Long id, PatientCreateDto updatePatientRequestDto) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));
        modelMapper.map( updatePatientRequestDto,patient);

        return modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePartialPatient(Long id, PatientUpdateDto updatePartialPatientRequestDto) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));
        modelMapper.map( updatePartialPatientRequestDto,patient);

        return modelMapper.map(patient,PatientResponseDto.class);
    }
}
