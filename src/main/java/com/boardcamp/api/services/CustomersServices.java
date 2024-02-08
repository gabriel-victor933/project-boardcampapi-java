package com.boardcamp.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomersDto;
import com.boardcamp.api.errors.NotFoundEntityException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.repositories.CustomersRepository;

@Service
public class CustomersServices {
    
    @Autowired
    private CustomersRepository customersRepository;

    public CustomersModel getCustomerById(Long id) throws NotFoundEntityException{
        Optional<CustomersModel> customer = customersRepository.findById((Long) id);

        if(!customer.isPresent()){
            throw new NotFoundEntityException("Customer Not Found!");
        }
        return customer.get();
    }

    public CustomersModel postCustomer(CustomersDto customer){
        return customersRepository.save(new CustomersModel(customer));
    }
}
