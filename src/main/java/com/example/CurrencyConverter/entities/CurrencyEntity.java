package com.example.CurrencyConverter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public Double exchangeRate;
    public Double currency;
}
