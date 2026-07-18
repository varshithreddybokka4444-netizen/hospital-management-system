package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourseNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    public final ModelMapper modelMapper;
    @Transactional(readOnly = true)
    @Override
    public Optional<PatientResponseDto> getPatientById(Long id){
        Patient p1 =   patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));

        return Optional.of(modelMapper.map(p1,PatientResponseDto.class));
    }

//    public void deleteById(long id) {
//        patientRepository.deleteById(id);
//    }

    @Override
    public PatientResponseDto registerNewPatient(@Valid PatientRequestDto addPatientRequestDto) {

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
    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));
        patientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(Long id, PatientRequestDto addPatientRequestDto) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourseNotFoundException("Patient not found with id "+id));
        modelMapper.map( addPatientRequestDto,patient);

        return modelMapper.map(patient,PatientResponseDto.class);
    }
}
