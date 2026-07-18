package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorUpdateDto {
    private String name;


    private String specialisation;

    
    @Email
    private String email;
}
