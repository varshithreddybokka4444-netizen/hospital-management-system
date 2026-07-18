package com.codingshuttle.youtube.hospitalManagement.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
