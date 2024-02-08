package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalsDto;
import com.boardcamp.api.errors.NotFoundEntityException;
import com.boardcamp.api.errors.UnprocessableEntityException;
import com.boardcamp.api.services.RentalsService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("rentals")
public class RentalsController {

    @Autowired
    private RentalsService rentalsService;
    
    @GetMapping
    public ResponseEntity<Object> getRentals() {
        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.getRentals());
    }

    @PostMapping()
    public ResponseEntity<Object> postRental(@RequestBody @Valid RentalsDto rental) throws  UnprocessableEntityException, NotFoundEntityException {
        

        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.postRental(rental));
        
        
    }
    
    @PutMapping("/{id}/return")
    public ResponseEntity<Object> finishRental(@PathVariable int id) throws  UnprocessableEntityException, NotFoundEntityException {
        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.finishRental((long) id));

    }
}
