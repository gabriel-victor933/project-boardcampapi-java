package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamesDto {
    @NotBlank
    private String name;
    @NotBlank
    private String image;
    @NotNull
    @Positive
    private int stockTotal;
    @NotNull
    @Positive
    private int pricePerDay; 
}
