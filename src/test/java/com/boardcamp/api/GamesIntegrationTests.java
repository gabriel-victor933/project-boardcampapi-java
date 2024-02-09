package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.GamesDto;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GamesIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RentalsRepository rentalsRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private GamesRepository gamesRepository;

    @AfterEach
    public void erasaDbs(){
        rentalsRepository.deleteAll();
        customersRepository.deleteAll();
        gamesRepository.deleteAll();
    }

    @Test
    void givenSavedGamesWhenGettingThenReturnOk(){
        gamesRepository.save(new GamesModel(null,"Title1","http",10,1000));

        ResponseEntity<List> res = restTemplate.getForEntity("/games", List.class);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(1, res.getBody().size());
    }

    @Test
    void givenInvalidBodyWhenPostingGameThenReturnBadRequest(){
        GamesDto game = new GamesDto();

        HttpEntity<GamesDto> body = new HttpEntity<GamesDto>(game);

        ResponseEntity<String> res = restTemplate.exchange("/games", HttpMethod.POST,body,String.class);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    void givenValidBodyWhenPostingGameThenReturnCreated(){
        GamesDto game = new GamesDto("title1","Http",10,1000);

        HttpEntity<GamesDto> body = new HttpEntity<GamesDto>(game);

        ResponseEntity<String> res = restTemplate.exchange("/games", HttpMethod.POST,body,String.class);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

}
