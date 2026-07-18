package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.exceptions.ResourseNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Doctor not found with id "+id));


        return modelMapper.map(doctor,DoctorResponseDto.class);
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
            throw new ResourseNotFoundException("No doctors found");
        }
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Doctor not found with id "+id));
        doctorRepository.delete(doctor);

    }

    @Override
    @Transactional
    public DoctorResponseDto updateDoctor(Long id, DoctorCreateDto doctorUpdateRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Doctor not found with id "+id));

        modelMapper.map(doctor,doctorUpdateRequest);
        return modelMapper.map(doctor,DoctorResponseDto.class);
    }

    @Override
    @Transactional
    public DoctorResponseDto updatePartialDoctor(Long id, DoctorUpdateDto updatePartialDoctorUpdateDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Doctor not found with id "+id));

        modelMapper.map(doctor,updatePartialDoctorUpdateDto);
        return modelMapper.map(doctor,DoctorResponseDto.class);
    }


}
