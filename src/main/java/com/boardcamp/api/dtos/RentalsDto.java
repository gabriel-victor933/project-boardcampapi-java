package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalsDto {
    @NotNull
    @Positive
    private int daysRented;
    @NotNull
    @Positive
    private long customerId;
    @NotNull
    @Positive
    private long gameId;

}
