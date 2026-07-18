package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.PatientRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.PatientResponseDto;
import com.codingshuttle.youtube.hospitalManagement.service.PatientService;
import com.codingshuttle.youtube.hospitalManagement.service.impl.PatientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/patients")
public class PatientController {


    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createNewPatient(@RequestBody @Valid PatientRequestDto addPatientRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.registerNewPatient(addPatientRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<PatientResponseDto>> getPatientById(@PathVariable("id") Long id){
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
    public ResponseEntity<PatientResponseDto> updateStudent(@PathVariable Long id,
                                                    @RequestBody PatientRequestDto addPatientRequestDto){
        return ResponseEntity.ok(patientService.updatePatient(id,addPatientRequestDto));
    }
}
