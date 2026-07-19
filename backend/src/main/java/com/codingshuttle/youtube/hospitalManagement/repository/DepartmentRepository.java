package com.codingshuttle.youtube.hospitalManagement.repository;

import com.codingshuttle.youtube.hospitalManagement.entity.Department;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department d where d.publicId = :publicId")
    public Optional<Department>findByPublicId(String publicId);

}