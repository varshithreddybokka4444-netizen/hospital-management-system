package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class DepartmentUpdateDto {


    private String name;


    private String headDoctorPublicId;

}
