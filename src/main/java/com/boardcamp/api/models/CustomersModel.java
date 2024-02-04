package com.boardcamp.api.models;

import com.boardcamp.api.dtos.CustomersDto;

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
@Table(name="customers")
public class CustomersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false) 
    private String name;
    @Column(unique = true, length = 11, nullable = false) 
    private String cpf;

    public CustomersModel(CustomersDto customer){
        this.name = customer.getName();
        this.cpf = customer.getCpf();
    }
}
