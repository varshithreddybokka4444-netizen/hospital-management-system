package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType.O_POSITIVE;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    PatientServiceImpl patientService;

    @Test
    public void testPatientRegistrationMethod(){
        PatientCreateDto newRequest = PatientCreateDto.builder()
                .name("Ravi")
                .birthDate(LocalDate.of(1988,2,2))
                .email("ravi@gmail.com")
                .gender("Male")
                .bloodGroup(O_POSITIVE).build();


        PatientResponseDto patientDto = patientService.registerNewPatient(newRequest);

        System.out.println(patientDto);
    }
}