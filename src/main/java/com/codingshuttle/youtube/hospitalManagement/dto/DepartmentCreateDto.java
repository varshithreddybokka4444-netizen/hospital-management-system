package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class DepartmentCreateDto {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Head doctor public ID is required")
    private String headDoctorPublicId;

}
