package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.AddPatientRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType.O_POSITIVE;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    PatientService patientService;

    @Test
    public void testPatientRegistrationMethod(){
        AddPatientRequestDto newRequest = AddPatientRequestDto.builder()
                .name("Ravi")
                .birthDate(LocalDate.of(1988,2,2))
                .email("ravi@gmail.com")
                .gender("Male")
                .bloodGroup(O_POSITIVE).build();


        PatientDto patientDto = patientService.registerNewPatient(newRequest);

        System.out.println(patientDto);
    }
}