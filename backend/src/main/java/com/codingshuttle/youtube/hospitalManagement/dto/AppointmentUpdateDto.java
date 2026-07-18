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
public class AppointmentUpdateDto {

    private String patientPublicId;

    private String doctorPublicId;

    private LocalDateTime appointmentTime;

    @Size(min = 10, max = 200, message = "Reason should be length 10 to 200 characters")
    private String reason;


}
