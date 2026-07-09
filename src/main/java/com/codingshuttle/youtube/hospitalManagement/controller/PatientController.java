package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {


    private final PatientService patientService;



    @PostMapping
    public ResponseEntity<PatientResponseDto> createNewPatient(@RequestBody @Valid PatientCreateDto addPatientRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.registerNewPatient(addPatientRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable Long id){
        patientService.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatepatient(@PathVariable Long id,
                                                    @RequestBody PatientCreateDto addPatientRequestDto){
        return ResponseEntity.ok(patientService.updatePatient(id,addPatientRequestDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePartialPatient(@PathVariable Long id,
                                                                   @RequestBody PatientUpdateDto updatePartialPatientRequestDto){

        return ResponseEntity.ok(patientService.updatePartialPatient(id,updatePartialPatientRequestDto));
    }

}
