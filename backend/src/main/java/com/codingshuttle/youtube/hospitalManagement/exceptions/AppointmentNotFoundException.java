package com.codingshuttle.youtube.hospitalManagement.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
