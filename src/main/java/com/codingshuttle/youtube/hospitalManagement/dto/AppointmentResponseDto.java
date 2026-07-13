package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentResponseDto {
    private String publicId;

    private PatientSummaryDto patient;

    private DoctorSummaryDto doctor;

    private LocalDateTime appointmentTime;

    private String reason;


}
