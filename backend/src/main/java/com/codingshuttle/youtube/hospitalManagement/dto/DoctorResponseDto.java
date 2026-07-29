package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class DoctorResponseDto {
    private String publicId;

    private String name;

    private String specialisation;

    private String email;

    private DepartmentSummaryDto department;
}
