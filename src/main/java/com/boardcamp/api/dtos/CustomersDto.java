package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersDto {
    
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 11, max = 11, message = "CPF must hava eleven characters!")
    private String cpf;
}
