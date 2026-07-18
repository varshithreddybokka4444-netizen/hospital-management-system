package com.codingshuttle.youtube.hospitalManagement.exceptions;

public class InsuranceAlreadyAssignedException extends RuntimeException{
    public InsuranceAlreadyAssignedException(String message){
        super(message);
    }
}
