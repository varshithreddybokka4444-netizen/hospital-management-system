package com.codingshuttle.youtube.hospitalManagement.service.impl;

import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.exception.ResourceNotFoundException;
import com.codingshuttle.youtube.hospitalManagement.repository.AppointmentRepository;
import com.codingshuttle.youtube.hospitalManagement.service.AppointmentService;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository ;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AppointmentResponseDto createNewAppointment(AppointmentCreateDto appointmentCreateRequest){

        Doctor doctor = doctorService.getDoctorEntityByPublicId(appointmentCreateRequest.getDoctorPublicId());
        Patient patient = patientService.getPatientEntityByPublicId(appointmentCreateRequest.getPatientPublicId());

        Appointment appointment  = Appointment.builder()
                        .appointmentTime(appointmentCreateRequest.getAppointmentTime())
                .reason(appointmentCreateRequest.getReason())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);


        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

         appointmentRepository.save(appointment);

         return modelMapper.map(appointment,AppointmentResponseDto.class);
    }

    @Override
    public AppointmentResponseDto getAppointmentByPublicId(String publicId) {
        Appointment appointment = getAppointmentEntityByPublicId(publicId);
        return modelMapper.map(appointment,AppointmentResponseDto.class);
    }

    public Appointment getAppointmentEntityByPublicId(String publicId) {
        Appointment appointment = appointmentRepository.findByPublicId(publicId).orElseThrow(()->new ResourceNotFoundException("Appointment not found with publicId :"+publicId));
        return appointment;
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream()
                .map(appointment->modelMapper.map(appointment,AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDto updateAppointment(String publicId, AppointmentCreateDto updateAppointmentRequestDto) {
        Appointment appointment = getAppointmentEntityByPublicId(publicId);

        Doctor doctor = doctorService.getDoctorEntityByPublicId(updateAppointmentRequestDto.getDoctorPublicId());
        Patient patient = patientService.getPatientEntityByPublicId(updateAppointmentRequestDto.getPatientPublicId());


        appointment.setAppointmentTime(updateAppointmentRequestDto.getAppointmentTime());
        appointment.setReason(updateAppointmentRequestDto.getReason());

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return modelMapper.map(appointment,AppointmentResponseDto.class);
    }

    @Override
    public void cancelAppointmentByPublicId(String publicId) {
        Appointment appointment = getAppointmentEntityByPublicId(publicId);
        appointmentRepository.delete(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponseDto partialUpdateAppointment(String publicId, AppointmentUpdateDto partialUpdateAppointmentRequestDto) {
        Appointment appointment = getAppointmentEntityByPublicId(publicId);

        if(partialUpdateAppointmentRequestDto.getDoctorPublicId()!=null) {
            Doctor doctor = doctorService.getDoctorEntityByPublicId(partialUpdateAppointmentRequestDto.getDoctorPublicId());
            appointment.setDoctor(doctor);
        }
        if(partialUpdateAppointmentRequestDto.getPatientPublicId()!=null) {
            Patient patient = patientService.getPatientEntityByPublicId(partialUpdateAppointmentRequestDto.getPatientPublicId());
            appointment.setPatient(patient);
        }

        if(partialUpdateAppointmentRequestDto.getAppointmentTime()!=null) {
            appointment.setAppointmentTime(partialUpdateAppointmentRequestDto.getAppointmentTime());
        }
        if(partialUpdateAppointmentRequestDto.getReason()!=null) {
            appointment.setReason(partialUpdateAppointmentRequestDto.getReason());
        }

        return modelMapper.map(appointment,AppointmentResponseDto.class);
    }


//    @Transactional
//    public Appointment reassignAppointment(Long appointmentId, Long newDoctorId){
//
//        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
//
//        Doctor doctor = doctorRepository.findById(newDoctorId).orElseThrow();
//
//        appointment.setDoctor(doctor);
//
//        doctor.getAppointments().add(appointment);//just for bidirectional consistency
//
//        return appointment;
//
//    }

}
