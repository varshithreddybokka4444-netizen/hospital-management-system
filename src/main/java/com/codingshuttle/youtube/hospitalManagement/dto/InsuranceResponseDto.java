package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InsuranceResponseDTO {
    private String publicId;

    private String policyNumber;

    private String provider;

    private LocalDate validUntil;
}
