package com.codingshuttle.youtube.hospitalManagement.repository;

import com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findByName(String name);
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);
    List<Patient> findByBirthDateBetween(LocalDate startDate,LocalDate endDate);
//    List<Patient> findByAgeLessThan(int age);
//    List<Patient> findByAgegreaterThan(int age);

    List<Patient> findByNameContainingOrderByIdDesc(String query);

    @Query("SELECT p FROM Patient p where p.bloodGroup = :bloodGroup")
    List<Patient> findByBloodGroup(@Param("bloodGroup")BloodGroupType bloodGroup);

    @Query("SELECT p FROM Patient p where p.birthDate > ?1"  )
    List<Patient> findByBornAfterDate(LocalDate birthDate);

//    @Query("SELECT p.bloodGroup,count(p) from Patient p group by p.bloodGroup") -> jpql
//    List<Object[]> countEachBloodGroupType();

//    @Query(value = "SELECT p.blood_group,count(*) from patient p group by p.blood_group",nativeQuery = true)//-> sql or native query, we must use value = query
//    List<Object[]> countEachBloodGroupType();

    @Query("SELECT new com.codingshuttle.youtube.hospitalManagement.dto.BloodGroupCountResponseEntity( p.bloodGroup,count(p) )from Patient p group by p.bloodGroup")
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    @Transactional
    @Modifying
    @Query("Update Patient p set p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name")String name,@Param("id")Long id);

    @Query("SELECT p FROM Patient p")
    Page<Patient> findAllPatients(Pageable pageable);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor ")
    List<Patient> findAllPatientsWithAppointment();

}
