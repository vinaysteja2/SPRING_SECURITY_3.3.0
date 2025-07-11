package com.travelbank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbank.model.Cards;
import com.travelbank.model.Customer;
import com.travelbank.repository.CardsRepository;
import com.travelbank.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;

    private final CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            List<Cards> cards = cardsRepository.findByCustomerId(optionalCustomer.get().getId());
            if (cards != null) {
                return cards;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}