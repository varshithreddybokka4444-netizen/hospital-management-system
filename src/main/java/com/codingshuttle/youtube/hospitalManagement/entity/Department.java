package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false,length = 12)
    private String publicId;

    @Column(nullable = false, unique = true,length = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "head_doctor_id",nullable = false)
    private Doctor headDoctor;

    @ManyToMany
    @JoinTable(
            name = "my_dpt_doctors",
            joinColumns = @JoinColumn(name = "dpt_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private Set<Doctor> doctors = new HashSet<>();

    @PrePersist
    protected void onCreate(){
        if(this.publicId==null){
            this.publicId = java.util.UUID.randomUUID().toString().substring(0,12);
        }
    }
}
