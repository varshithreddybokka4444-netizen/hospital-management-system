package com.codingshuttle.youtube.hospitalManagement.controller;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DoctorUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.service.DoctorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Negative;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> registerNewDoctor(@RequestBody @Valid DoctorCreateDto addDoctorRequesDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.registerNewDoctor(addDoctorRequesDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto>getDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }


    @GetMapping()
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorById(@PathVariable Long id){
        doctorService.deleteDoctorById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable Long id,
                                                           @RequestBody DoctorCreateDto updateDoctorRequestDto){
        return ResponseEntity.ok(doctorService.updateDoctor(id,updateDoctorRequestDto));
    }

    @PostMapping("/{id")
    public ResponseEntity<DoctorResponseDto> updatePartialDoctor(@PathVariable Long id,
                                                                 @RequestBody DoctorUpdateDto updatePartialDoctorUpdateDto){
        return ResponseEntity.ok(doctorService.updatePartialDoctor(id,updatePartialDoctorUpdateDto));
    }
}
