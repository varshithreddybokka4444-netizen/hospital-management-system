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
    public AppointmentResponseDto createNewAppointment(AppointmentCreateDto appointmentCreateDto){

        Doctor doctor = doctorService.getDoctorEntityByPublicId(appointmentCreateDto.getDoctorPublicId());
        Patient patient = patientService.getPatientEntityByPublicId(appointmentCreateDto.getPatientPublicId());

        Appointment appointment  = modelMapper.map(appointmentCreateDto,Appointment.class);
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

        if(appointments.isEmpty()){
            throw new ResourceNotFoundException("No appointments found");
        }


        return appointments.stream()
                .map(appointment->modelMapper.map(appointment,AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDto updateAppointment(String publicId, AppointmentCreateDto updateAppointmentRequestDto) {
        Appointment appointment = getAppointmentEntityByPublicId(publicId);
        modelMapper.map(updateAppointmentRequestDto,appointment);
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
        modelMapper.map(partialUpdateAppointmentRequestDto,appointment);
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
