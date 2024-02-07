package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalsDto;
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
    public ResponseEntity<Object> postRental(@RequestBody @Valid RentalsDto rental) {
        
        try {

            return ResponseEntity.status(HttpStatus.OK).body(rentalsService.postRental(rental));

        } catch(UnprocessableEntityException e){

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());

        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    @PutMapping("/{id}/return")
    public ResponseEntity<Object> finishRental(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rentalsService.finishRental((long) id));
            
        } catch (Exception e) {
            
            System.out.println(e.getClass());
            return ResponseEntity.status(500).body(null);
        }
    }
}
