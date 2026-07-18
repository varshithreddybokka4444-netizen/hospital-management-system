package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class AppointmentCreateDto {

    @NotBlank(message = "Patient publicid is required")
    private String patientPublicId;

    @NotBlank(message = "Doctor publicid is required")
    private String doctorPublicId;

    @NotBlank(message = "Appointment time is Required")
    private LocalDateTime appointmentTime;

    @NotBlank(message = "Reason is Required")
    @Size(min = 10, max = 200, message = "Reason should be length 10 to 200 characters")
    private String reason;


}
