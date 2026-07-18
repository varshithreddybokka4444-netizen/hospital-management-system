package com.codingshuttle.youtube.hospitalManagement.dto;

import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import com.codingshuttle.youtube.hospitalManagement.entity.type.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PatientcreateDto {

    @NotBlank(message = "Name is Required")
    @Size(min = 3, max = 30, message = "Name should be of length 3 to 30 characters")
    private String name;

    @NotNull(message = "BirthDate is required")
    private LocalDate birthDate;

    @NotBlank(message = "Email is Required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Gender is required")
    private GenderType gender;

    @NotNull(message = "BloodGroup is required")
    private BloodGroupType bloodGroup;

}
