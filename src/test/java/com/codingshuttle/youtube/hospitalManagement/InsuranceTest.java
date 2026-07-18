package com.codingshuttle.youtube.hospitalManagement;

import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.service.impl.AppointmentServiceImpl;
import com.codingshuttle.youtube.hospitalManagement.service.impl.InsuranceServiceImpl;
import com.codingshuttle.youtube.hospitalManagement.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {


    @Autowired
    private InsuranceServiceImpl insuranceService;

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    PatientServiceImpl patientService;

    @Test
    public void testInsurance(){

        Insurance insurance = Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030,12,12))
                .build();

        Patient patient = insuranceService.assignInsurance(insurance,1L);

        System.out.println(patient);


        var newPatient = insuranceService.disassociateInsuranceFromPatient(patient.getId());

        System.out.println(newPatient);
    }

    @Test
    public void testCreateAppointment(){
//        Appointment appointment = Appointment.builder()
//                .appointmentTime(LocalDateTime.of(2025,11,1,15,00,00))
//                .reason("Cancer")
//                .build();
//
//        var newAppointment = appointmentService.createNewAppointment(appointment,3L,4L);
//
//        System.out.println(newAppointment);
//
//        var updatedAppointment = appointmentService.reassignAppointment(newAppointment.getId(),2L);
//
//        System.out.println(updatedAppointment);
//
//
        Appointment appointment1 = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,11,2,15,00,00))
                .reason("Blood infection")
                .build();

        var newAppointment1 = appointmentService.createNewAppointment(appointment1,1L,4L);

        System.out.println(appointment1);

        Appointment appointment2 = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,11,3,15,00,00))
                .reason("Blood Cancer")
                .build();

        var newAppointment2 = appointmentService.createNewAppointment(appointment2,2L,4L);
        System.out.println(appointment2);

        Appointment appointment3 = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,11,4,15,00,00))
                .reason("Blood Cancer")
                .build();

        var newAppointment3 = appointmentService.createNewAppointment(appointment3,3L,4L);
        System.out.println(appointment3);


        patientService.deletePatientById(4L);
    }



}
