package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 12)
    private String publicId ;


    @Column(nullable = false, unique = true, length = 58)
    private String policyNumber;

    @Column(nullable = false, length = 100)
    private String provider;

    @NotNull(message = "Expire date is required")
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "insurance")//inverse side
    private Patient patient;

    @PrePersist
    protected void onCreate(){
        if(this.publicId==null){
            this.publicId = java.util.UUID.randomUUID().toString().substring(0,8);
        }
    }

}