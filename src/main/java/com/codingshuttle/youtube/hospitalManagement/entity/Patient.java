package com.codingshuttle.youtube.hospitalManagement.entity;


import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import com.codingshuttle.youtube.hospitalManagement.entity.type.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;


@Entity
@ToString
@Getter
@Setter
@Table(
        name = "Patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate",columnNames = {"name","birthDate"})
        },
        indexes = {
                @Index(name = "index_patient_birthdate", columnList = "birthDate")

        }

)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 12)
    private String publicId ;

    @Column(nullable =false, length = 40)
    private String name;

//    @ToString.Exclude
    private LocalDate birthDate;

    @Column(unique=true,nullable= false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private GenderType gender;



    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Transient
    public int getAge(){
        return Period.between(birthDate,LocalDate.now()).getYears();
    }
    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id")//owning side
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        // 1. Automatically generate the unique random token if null


        if(this.publicId==null){
            this.publicId = java.util.UUID.randomUUID().toString().substring(0,12);
        }
       //  2. Fix the casing bug! Force the gender string to uppercase so Hibernate can read it
        if (this.gender != null) {
            try {
                String upperGender = this.gender.toString().trim().toUpperCase();
                this.gender = GenderType.valueOf(upperGender);
            } catch (IllegalArgumentException e) {
                // Optional: fallback or default if someone sends completely invalid text
                this.gender = null;
            }
        }


    }


}
