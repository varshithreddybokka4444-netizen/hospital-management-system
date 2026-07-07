package com.codingshuttle.youtube.hospitalManagement.dto;

import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PatientResponseDto {

    private String publicId;

    private String name;

    private LocalDate birthDate;

    private int age;

    private String email;

    private String gender;

    private BloodGroupType bloodGroup;

}
