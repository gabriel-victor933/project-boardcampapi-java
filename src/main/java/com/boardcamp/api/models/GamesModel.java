package com.boardcamp.api.models;

import com.boardcamp.api.dtos.GamesDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class GamesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true,length = 150, nullable = false) 
    private String name;
    @Column( nullable = false)
    private String image;
    @Column( nullable = false)
    private int stockTotal;
    @Column(nullable = false)
    private int pricePerDay; 

    public GamesModel(GamesDto game){
        this.name = game.getName();
        this.image = game.getImage();
        this.stockTotal = game.getStockTotal();
        this.pricePerDay = game.getPricePerDay();
    }
}