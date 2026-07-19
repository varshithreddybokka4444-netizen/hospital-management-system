package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.*;
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
    @GetMapping("/{publicId}")
    public ResponseEntity<PatientResponseDto> getPatientByPublicId(@PathVariable String publicId){
        return ResponseEntity.ok(patientService.getPatientByPublicId(publicId));
    }
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletePatientById(@PathVariable String publicId){
        patientService.deletePatientByPublicId( publicId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{publicId}")
    public ResponseEntity<PatientResponseDto> updatepatient(@PathVariable String publicId,
                                                    @RequestBody @Valid  PatientCreateDto addPatientRequestDto){
        return ResponseEntity.ok(patientService.updatePatient(publicId,addPatientRequestDto));
    }

    @PostMapping("/{publicId}")
    public ResponseEntity<PatientResponseDto> updatePartialPatient(@PathVariable String publicId,
                                                                   @RequestBody @Valid PatientUpdateDto updatePartialPatientRequestDto){

        return ResponseEntity.ok(patientService.updatePartialPatient(publicId,updatePartialPatientRequestDto));
    }

    @PostMapping("/insurance")
    public ResponseEntity<InsuranceResponseDto>  assignInsurance(@PathVariable String publicId,
                                                                 @RequestBody @Valid  InsuranceCreateDto assignInsuranceRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.assignInsurance(publicId,assignInsuranceRequestDto));
    }

    @DeleteMapping("/insurance/{publicId}")
    public ResponseEntity<Void> dissociateInsuranceOfPatientByPublicId(@PathVariable String patientPublicId){
        patientService.dissociateInsurance(patientPublicId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/insurance/{publicId}")
    public ResponseEntity<InsuranceResponseDto> updateInsuranceOfPatientByPublicid(@PathVariable String publicId,
                                                                                   @RequestBody @Valid  InsuranceCreateDto updateInsuranceRequestDto){
        return ResponseEntity.ok(patientService.updateInsurance(publicId,updateInsuranceRequestDto));
    }

    @PatchMapping("/insurance/{publicId}")
    public ResponseEntity<InsuranceResponseDto> partialUpdateInsuranceOfPatientByPublicid(@PathVariable String publicId,
                                                                                   @RequestBody @Valid  InsuranceUpdateDto partialUpdateInsuranceRequestDto){
        return ResponseEntity.ok(patientService.partialUpdateInsurance(publicId,partialUpdateInsuranceRequestDto));
    }

    @GetMapping("/insurance/{publicId}")
    public ResponseEntity<InsuranceResponseDto> getInsuranceOfPatientByPublicId(@PathVariable String publicId){
        return ResponseEntity.ok(patientService.getInsuranceOfPatient(publicId));
    }

}
