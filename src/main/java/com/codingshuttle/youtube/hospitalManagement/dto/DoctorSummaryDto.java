package com.codingshuttle.youtube.hospitalManagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorSummaryDto {
    private String publicid;
    private String name;
    private String specialisation;
}
