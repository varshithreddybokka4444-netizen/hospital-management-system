package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    public final ModelMapper modelMapper;
    @Transactional
    public Patient getPatientById(Long id){
        Patient p1 = patientRepository.findById(id).orElseThrow();

        return p1;
    }

    public void deleteById(long id) {
        patientRepository.deleteById(id);
    }

  public @Nullable PatientResponseDto registerNewPatient(@Valid PatientRequestDto addPatientRequestDto) {

        Patient newPatient = modelMapper.map(addPatientRequestDto,Patient.class);
        Patient patient = patientRepository.save(newPatient);

      return modelMapper.map(patient, PatientResponseDto.class);
  }
}
