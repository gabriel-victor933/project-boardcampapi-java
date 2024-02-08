package com.boardcamp.api.errors;

public class NotFoundEntityException extends Exception {
    
    public NotFoundEntityException(String message){
        super(message);
    }
}
