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

}
