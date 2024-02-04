package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.GamesDto;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("games")
public class GamesController {

    @Autowired
    private GamesRepository gamesRepository;
    
    @GetMapping
    public ResponseEntity<List<GamesModel>> getGames() {
        return ResponseEntity.status(HttpStatus.OK).body(gamesRepository.findAll());
    }
    
    @PostMapping()
    public ResponseEntity<Object> PostGame(@RequestBody @Valid GamesDto game) {

        try {
            GamesModel gameCreated = gamesRepository.save(new GamesModel(game));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(gameCreated);
        } catch (DataIntegrityViolationException e) {
            
            System.out.println(e.getClass());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Name is already in use!");
        }


    }
    
}
