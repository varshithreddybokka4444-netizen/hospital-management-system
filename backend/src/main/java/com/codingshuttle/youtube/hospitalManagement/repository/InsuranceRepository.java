package com.codingshuttle.youtube.hospitalManagement.repository;

import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    @Query("select i from Doctor i where i.publicId = :publicId")
    public Optional<Insurance> findByPublicId(String publicId);
}