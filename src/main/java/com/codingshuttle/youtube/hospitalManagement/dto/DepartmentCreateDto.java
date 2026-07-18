package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DepartmentRequestDTO {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Head doctor public ID is required")
    private String headDoctorPublicId;

}
