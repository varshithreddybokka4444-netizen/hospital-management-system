package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(length = 100)
    private String specialisation;

    @Column(unique = true,length = 100)
    private String email;
    
    @OneToMany(mappedBy = "doctor")
    @ToString.Exclude
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "doctors")
    @ToString.Exclude
    private Set<Department> departments = new HashSet<>();


}


