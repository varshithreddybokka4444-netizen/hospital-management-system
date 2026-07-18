package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentUpdateDto;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createNewAppointment( AppointmentCreateDto appointmentCreateDto);

    AppointmentResponseDto getAppointmentByPublicId(String publicId);

    List<AppointmentResponseDto> getAllAppointments();


    AppointmentResponseDto updateAppointment(String publicId, @Valid AppointmentCreateDto updateAppointmentRequestDto);

    void cancelAppointmentByPublicId(String publicId);

    AppointmentResponseDto partialUpdateAppointment(String publicId, @Valid AppointmentUpdateDto partialUpdateAppointmentRequestDto);
}
