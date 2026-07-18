package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourceNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with id "+id));


        return modelMapper.map(doctor,DoctorResponseDto.class);
    }

    @Override
    public Doctor getDoctorEntityByPublicId(String publicId) {


        return doctorRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with publicId " + publicId));
    }

    @Override
    public DoctorResponseDto getDoctorByPublicId(String publicId) {

        return modelMapper.map(getDoctorEntityByPublicId(publicId),DoctorResponseDto.class);
    }


    @Override
    public DoctorResponseDto registerNewDoctor(DoctorCreateDto addDoctorRequest) {
        Doctor newDoctor = modelMapper.map(addDoctorRequest,Doctor.class);

        Doctor savedDoctor = doctorRepository.save(newDoctor);

        return modelMapper.map(savedDoctor,DoctorResponseDto.class);
    }

    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        if(doctors.isEmpty()){
            throw new ResourceNotFoundException("No doctors found");
        }
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDoctorByPublicId(String publicId) {
        Doctor doctor = doctorRepository.findByPublicId(publicId)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with publicId "+publicId));
        doctorRepository.delete(doctor);

    }

    @Override
    @Transactional
    public DoctorResponseDto updateDoctor(String publicId, DoctorCreateDto doctorUpdateRequest) {
        Doctor doctor = doctorRepository.findByPublicId(publicId)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with publicId "+publicId));

        modelMapper.map(doctor,doctorUpdateRequest);
        return modelMapper.map(doctor,DoctorResponseDto.class);
    }

    @Override
    @Transactional
    public DoctorResponseDto updatePartialDoctor(String publicId, DoctorUpdateDto updatePartialDoctorUpdateDto) {
        Doctor doctor = doctorRepository.findByPublicId(publicId)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with publicId "+publicId));

        modelMapper.map(doctor,updatePartialDoctorUpdateDto);
        return modelMapper.map(doctor,DoctorResponseDto.class);
    }




}
