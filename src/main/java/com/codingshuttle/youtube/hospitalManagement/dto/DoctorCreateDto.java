package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorCreateDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Specialisation is required")
    private String specialisation;

    @NotBlank(message = "Please provide a valid email address")
    @Email
    private String email;
}
