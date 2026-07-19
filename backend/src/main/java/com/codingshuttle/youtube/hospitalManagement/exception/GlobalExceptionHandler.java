package com.codingshuttle.youtube.hospitalManagement.exception;

import com.codingshuttle.youtube.hospitalManagement.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlesResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request){

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(InsuranceAlreadyAssignedException.class)
    public ResponseEntity<ApiErrorResponse> handlesInsuranceAlreadyAssigned(
            InsuranceAlreadyAssignedException ex,
            HttpServletRequest request){

            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(HttpStatus.CONFLICT.value())
                    .error(HttpStatus.CONFLICT.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
    }

        @ExceptionHandler(InsuranceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handlesInsuranceNotFound(
                InsuranceNotFoundException ex,
                HttpServletRequest request){

            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlesPatientNotFound(
            PatientNotFoundException ex,
            HttpServletRequest request){

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }


    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlesDoctorNotFound(
            DoctorNotFoundException ex,
            HttpServletRequest request){

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

}
