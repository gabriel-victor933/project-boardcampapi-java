package com.boardcamp.api.errors;

public class UnprocessableEntityException extends Exception {

    public UnprocessableEntityException(String message){
        super(message);
    }
    
}