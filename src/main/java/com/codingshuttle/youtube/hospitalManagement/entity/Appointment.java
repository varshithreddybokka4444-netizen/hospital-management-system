package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,unique = true,updatable = false,length = 12)
    private String publicId;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)//an appointment without patient does not make sense
    @ToString.Exclude
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",nullable = false)//an appointment without patient does not make sense
    @ToString.Exclude
    private Doctor doctor;

    @PrePersist
    protected void onCreate(){
        if(this.publicId==null){
            this.publicId = java.util.UUID.randomUUID().toString().substring(0,12);
        }
    }

}
