package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.InsuranceUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.exceptions.InsuranceAlreadyAssignedException;
import com.codingshuttle.youtube.hospitalManagement.exceptions.InsuranceNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.InsuranceRepository;
import com.codingshuttle.youtube.hospitalManagement.service.InsuranceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public InsuranceResponseDto createInsurance(Patient patient, InsuranceCreateDto assignInsuranceRequestDto) {
        if(patient.getInsurance()!=null){
            throw new InsuranceAlreadyAssignedException("Patient already has insurance policy");
        }
        Insurance insurance = modelMapper.map(assignInsuranceRequestDto,Insurance.class);
        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        Insurance savedInsurance = insuranceRepository.save(insurance);

        return modelMapper.map(savedInsurance,InsuranceResponseDto.class);
    }

    @Override
    @Transactional
    public InsuranceResponseDto removeInsurance(Patient patient) {
        if(patient.getInsurance()==null){
            throw new InsuranceNotFoundException("No insurance found for patient with publicId: " + patient.getPublicId());
        }
        return modelMapper.map(patient.getInsurance(),InsuranceResponseDto.class);
    }

    @Override
    @Transactional
    public InsuranceResponseDto updateInsurance(Patient patient, InsuranceCreateDto updateInsuranceRequestDto) {
        if(patient.getInsurance()==null){
            throw new InsuranceNotFoundException("No insurance found for patient with publicId: " + patient.getPublicId());
        }

        Insurance insurance = patient.getInsurance();

         modelMapper.map(insurance,updateInsuranceRequestDto);

        return modelMapper.map(insurance,InsuranceResponseDto.class);

    }

    @Override
    @Transactional
    public InsuranceResponseDto partialUpdateInsurance(Patient patient, InsuranceUpdateDto partilUpdateInsuranceRequestDto) {
        if(patient.getInsurance()==null){
            throw new InsuranceNotFoundException("No insurance found for patient with publicId: " + patient.getPublicId());
        }
        Insurance insurance = patient.getInsurance();

        modelMapper.map(insurance,partilUpdateInsuranceRequestDto);

        return modelMapper.map(insurance,InsuranceResponseDto.class);
    }

    @Override
    public InsuranceResponseDto getInsuranceOfPatient(Patient patient) {
        if(patient.getInsurance()==null){
            throw new InsuranceNotFoundException("No insurance found for patient with publicId: " + patient.getPublicId());
        }

        return modelMapper.map(patient.getInsurance(),InsuranceResponseDto.class);
    }

    @Override
    public Insurance getInsuranceEntityByPatient(Patient patient) {
        if(patient.getInsurance()==null){
            throw new InsuranceNotFoundException("No insurance found for patient with publicId: " + patient.getPublicId());
        }

        return patient.getInsurance();
    }


}
