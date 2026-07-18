package com.codingshuttle.youtube.hospitalManagement.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InsuranceUpdateDto {

    @Size(min = 8,max = 50,message = "Policy number should be in range 8 to 50 characters")
    private String policyNumber;


    @Size(max = 100, message = "Provider cannot exceed 100 characters")
    private String provider;


    private LocalDate validUntil;



}

