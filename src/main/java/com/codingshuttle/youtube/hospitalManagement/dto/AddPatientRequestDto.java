package com.codingshuttle.youtube.hospitalManagement.dto;

import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Builder
@Data
public class AddPatientRequestDto {

    @NotBlank(message = "Name is Required")
    @Size(min = 3, max = 30, message = "Name should be of length 3 to 30 characters")
    private String name;

    @NotBlank(message = "BirthDate is required")
    private LocalDate birthDate;

    @NotBlank(message = "Email is Required")
    @Email
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "BloodGroup is required")
    private BloodGroupType bloodGroup;

}
