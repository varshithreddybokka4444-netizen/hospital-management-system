package com.codingshuttle.youtube.hospitalManagement.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class DoctorUpdateDto {
    private String name;


    private String specialisation;

    
    @Email
    private String email;

    private String departmentPublicId;
}
