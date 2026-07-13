package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentCreateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.DepartmentUpdateDto;
import com.codingshuttle.youtube.hospitalManagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment
            (@Valid @RequestBody DepartmentCreateDto addDepartmentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createNewDepartment(addDepartmentRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentByPublicId(@PathVariable String publicId){
        return ResponseEntity.ok(departmentService.getDepartmentById(publicId));
    }
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
        public ResponseEntity<DepartmentResponseDto>
                updateDepartment(@PathVariable Long id,@RequestBody DepartmentUpdateDto updateDepartmentRequestDto){
        return ResponseEntity.ok(departmentService.updateDepartment(id,updateDepartmentRequestDto));
        }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto>
    updatePartialDepartment(@PathVariable Long id,@RequestBody DepartmentUpdateDto partialUpdatepDepartmentRequestDto) {
        return ResponseEntity.ok(departmentService.updatePartialDepartment(id, partialUpdatepDepartmentRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto>
    assignNewHeadDoctor(@PathVariable Long id,@RequestBody DepartmentUpdateDto addNewHeadDoctorRequest) {
        return ResponseEntity.ok(departmentService.changeHeadDoctor(id, addNewHeadDoctorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartmentByPublicId(@PathVariable String publicId){

    }

    }


