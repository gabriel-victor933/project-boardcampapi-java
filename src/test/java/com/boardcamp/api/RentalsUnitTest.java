package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.RentalsDto;
import com.boardcamp.api.errors.NotFoundEntityException;
import com.boardcamp.api.errors.UnprocessableEntityException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;
import com.boardcamp.api.services.RentalsService;

@SpringBootTest
public class RentalsUnitTest {

    @InjectMocks
    private RentalsService rentalsService;
    
    @Mock
    private RentalsRepository rentalsRepository;
    @Mock
    private CustomersRepository customersRepository;
    @Mock
    private GamesRepository gamesRepository;

    @Test
    void givenNotExistingRentalWhenFindingRentalThenThrowsError(){
        //given 
        Long id = 1l;

        doReturn(Optional.empty()).when(rentalsRepository).findById(id);
        //when 
        NotFoundEntityException exception = assertThrows(NotFoundEntityException.class, () -> rentalsService.finishRental(id));
         //then
         assertNotNull(exception);
         assertEquals(NotFoundEntityException.class,exception.getClass());
    }

    @Test
    void givenRentalIdWhenValidateNotNullReturnDateThrowsError(){
        Long id = 1l;
        GamesModel game = new GamesModel();
        CustomersModel customer = new CustomersModel();
        RentalsModel rental = new RentalsModel(id,customer,game, LocalDate.now(),1,LocalDate.now(),1000,1000);

        doReturn(Optional.of(rental)).when(rentalsRepository).findById(id);

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> rentalsService.finishRental(id));

        assertNotNull(exception);
        assertEquals(UnprocessableEntityException.class, exception.getClass());
    }

    @Test 
    void givenNotExistingCustomerWhenPostingRentalThrowsError(){
        RentalsDto rental = new RentalsDto(1,1,1);

        doReturn(Optional.empty()).when(customersRepository).findById(rental.getCustomerId());

        NotFoundEntityException exception = assertThrows(NotFoundEntityException.class, () -> rentalsService.postRental(rental));

        assertNotNull(exception);
        assertEquals(NotFoundEntityException.class, exception.getClass());
    }

    @Test 
    void givenNotExistingGameWhenPostingRentalThrowsError(){
        RentalsDto rental = new RentalsDto(1,1,1);
        CustomersModel customer = new CustomersModel();

        doReturn(Optional.of(customer)).when(customersRepository).findById(rental.getCustomerId());

        doReturn(Optional.empty()).when(gamesRepository).findById(rental.getGameId());


        NotFoundEntityException exception = assertThrows(NotFoundEntityException.class, () -> rentalsService.postRental(rental));

        assertNotNull(exception);
        assertEquals(NotFoundEntityException.class, exception.getClass());
    }

    @Test 
    void givenGameWithNotEnoughFundsWhenPostingRentalThrowsError(){
        RentalsDto rental = new RentalsDto(1,1,1);
        CustomersModel customer = new CustomersModel();
        GamesModel game = new GamesModel(rental.getGameId(),"Game1","http",10,1000);

        doReturn(Optional.of(customer)).when(customersRepository).findById(rental.getCustomerId());

        doReturn(Optional.of(game)).when(gamesRepository).findById(rental.getGameId());

        doReturn(10).when(rentalsRepository).countRentalsByGameId(game.getId());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> rentalsService.postRental(rental));

        assertNotNull(exception);
        assertEquals(UnprocessableEntityException.class, exception.getClass());
    }
}
