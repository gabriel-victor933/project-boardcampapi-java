package com.boardcamp.api.models;

import java.time.LocalDate;

import com.boardcamp.api.dtos.RentalsDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="rentals")
public class RentalsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomersModel customer; 
    @ManyToOne
    @JoinColumn(name = "game_id")
    private GamesModel game;
    @Column(nullable = false)
    private LocalDate rentDate;
    @Column(nullable = false)
    private int daysRented;
    @Column(nullable = true)
    private LocalDate returnDate = null;
    @Column(nullable = false)
    private int originalPrice;
    @Column(nullable = false)
    private int delayFee = 0;

    public RentalsModel(RentalsDto rental, GamesModel game, CustomersModel customer){
        this.daysRented = rental.getDaysRented();
        this.game = game;
        this.customer = customer;
        this.originalPrice = game.getPricePerDay()*rental.getDaysRented();
        this.rentDate = LocalDate.now();
    }
}
