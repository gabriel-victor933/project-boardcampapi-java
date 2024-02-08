package com.boardcamp.api.errors;

import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalexceptionHandler {

    @ExceptionHandler({NotFoundEntityException.class})
    public ResponseEntity<String> handleNotFoundEntityException(NotFoundEntityException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<String> handleUnprocessableEntityException(UnprocessableEntityException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleGenericException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = "";
        BindingResult results = e.getBindingResult();
        for (FieldError error : results.getFieldErrors()) {
            System.out.println(error.getField() + " " + error.getDefaultMessage() + "\n");
            errorMessage += (error.getField() + " " + error.getDefaultMessage() + "\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


}