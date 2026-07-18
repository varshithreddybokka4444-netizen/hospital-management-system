package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class InsuranceCreateDto {
    @NotBlank(message = "Policy number is required")
    @Size(min = 8,max = 50,message = "Policy number should be in range 8 to 50 characters")
    private String policyNumber;

    @NotBlank(message = "Provider is required")
    @Size(max = 100, message = "Provider cannot exceed 100 characters")
    private String provider;

    @NotNull(message = "Expire date is required")
    private LocalDate validUntil;



}
