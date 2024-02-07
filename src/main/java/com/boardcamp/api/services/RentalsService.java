package com.boardcamp.api.services;

import java.time.LocalDate;
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

    public RentalsModel finishRental(long id) throws NotFoundException, UnprocessableEntityException{
        Optional<RentalsModel> rental = rentalsRepository.findById(id);

        if(!rental.isPresent()) throw new NotFoundException();

        if(rental.get().getReturnDate() != null) throw new UnprocessableEntityException("Rental have been finished!");
       
        LocalDate returnDate = LocalDate.now();
        int delayFee = calculteDelayfee(returnDate,rental.get().getRentDate(),rental.get().getDaysRented(),rental.get().getGame().getPricePerDay());

        RentalsModel updatedRental = rental.get();
        updatedRental.setReturnDate(returnDate);
        updatedRental.setDelayFee(delayFee);
        
        return rentalsRepository.save(updatedRental);
    }

    private int calculteDelayfee(LocalDate returnDate,LocalDate rentDate,int daysRented,int pricePerDay){

        int returnDateYear = returnDate.getYear();
        int rentDateYear = rentDate.getYear();

        if(returnDateYear == rentDateYear){
            
            if(daysRented < (returnDate.getDayOfYear() - rentDate.getDayOfYear())){
                return (returnDate.getDayOfYear() - rentDate.getDayOfYear() - daysRented)*pricePerDay;
            }

            return 0;
        } 

        int daysPast = 0;

        if (returnDateYear - rentDateYear > 1) {
            daysPast += (returnDateYear - rentDateYear - 1) * 365;
        }

        daysPast += returnDate.getDayOfYear() + (rentDate.lengthOfYear() - rentDate.getDayOfYear());

        return (daysPast - daysRented)*pricePerDay;
    }
}
