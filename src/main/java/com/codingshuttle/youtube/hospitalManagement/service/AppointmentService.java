package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.repository.AppointmentRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId){

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId()!=null) throw new IllegalArgumentException("Appointment should not have Id!");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reassignAppointment(Long appointmentId, Long newDoctorId){

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();

        Doctor doctor = doctorRepository.findById(newDoctorId).orElseThrow();

        appointment.setDoctor(doctor);

        doctor.getAppointments().add(appointment);//just for bidirectional consistency

        return appointment;

    }
}
