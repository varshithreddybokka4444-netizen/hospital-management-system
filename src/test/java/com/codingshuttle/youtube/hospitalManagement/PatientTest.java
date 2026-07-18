package com.codingshuttle.youtube.hospitalManagement;

import com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatientRepository() {

        List<Patient> patientList = patientRepository.findAllPatientsWithAppointment();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods() {
        //Patient p = patientService.getPatient(1L);


        // Patient patient = patientRepository.findByName("John Doe");

        //List<Patient> patientList =patientRepository.findByAgeGreaterThanEqual(28);
        //List<Patient> patientList =patientRepository.findByNameContainingOrderByIdDesc("Da");
        // List<Patient> patientList =patientRepository.findByBloodGroup(BloodGroupType.O_POSITIVE);
        //List<Patient> patientList =patientRepository.findByBornAfterDate(LocalDate.of(1993,1,1));

//    System.out.println("Size = " + bloodGroupList.size());
//
//    for(Object[] row : bloodGroupList){
//        System.out.println(row[0]+"   "+row[1]);
//
//        Patient p = patientRepository.findById(2L).orElseThrow();
//        System.out.println("Before updation  "+p.getName());
//        int rowsUpdated = patientRepository.updateNameWithId("Janey Smith",2L);
//
//        Patient changed_p = patientRepository.findById(2L).orElseThrow();
//
//        System.out.println("After updation  "+changed_p.getName());

//

        Page<Patient> patientList = patientRepository.findAllPatients(PageRequest.of(0,5, Sort.by("birthDate")));

        for(Patient patient : patientList.getContent())
            System.out.println(patient);

        }

    }

