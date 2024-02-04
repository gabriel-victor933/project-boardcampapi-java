package com.boardcamp.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomersDto;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.services.CustomersServices;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("customers")
public class CustomersController {
    
    @Autowired
    private CustomersServices customersServices;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable int id) {
        
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customersServices.getCustomerById((long) id));
        } catch (NotFoundException e) {
            
            System.out.println(e.getClass());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found!");
        }
    }

    @PostMapping
    public ResponseEntity<Object> postCustomer(@RequestBody @Valid CustomersDto customer) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customersServices.postCustomer(customer));
        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        }
    }
    
    
}
