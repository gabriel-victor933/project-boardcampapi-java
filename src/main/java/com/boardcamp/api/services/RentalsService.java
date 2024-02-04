package com.boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalsDto;
import com.boardcamp.api.errors.UnprocessableEntityException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@Service
public class RentalsService {
    
    @Autowired
    private RentalsRepository rentalsRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private GamesRepository gamesRepository;

    public List<RentalsModel> getRentals(){
        return rentalsRepository.findAll();
    }

    public RentalsModel postRental(RentalsDto rental) throws NotFoundException,UnprocessableEntityException {
        Optional<CustomersModel> customer = customersRepository.findById(rental.getCustomerId());
        Optional<GamesModel> game = gamesRepository.findById(rental.getGameId());

        if(!customer.isPresent() || !game.isPresent()) {
            throw new NotFoundException();
        }

        int count = rentalsRepository.countRentalsByGameId(game.get().getId());

        if(count >= game.get().getStockTotal()){
            throw new UnprocessableEntityException("Game is unavailable!");
        }

        return rentalsRepository.save(new RentalsModel(rental, game.get(),customer.get()));
    }
}
