package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.AppointmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Departments")
@Validated
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@RequestBody @Valid AppointmentCreateDto createAppointmentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/publicId")
    public ResponseEntity<AppointmentResponseDto> getAppointmentByPublicId(@PathVariable String publicId){
        return ResponseEntity.ok(appointmentService.getAppointmentByPublicId(publicId));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PutMapping("/publicId")
    public ResponseEntity<AppointmentResponseDto> updateAppoinment(@PathVariable String publicId,@RequestBody @Valid AppointmentCreateDto UpdateAppointmentRequestDto){
        return ResponseEntity.ok(appointmentService.updateAppointment(publicId,UpdateAppointmentRequestDto));
    }


    @DeleteMapping("/publicId")
    public ResponseEntity<Void> cancelAppointment(@PathVariable String publicId){
        appointmentService.cancelAppointmentByPublicId(publicId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

