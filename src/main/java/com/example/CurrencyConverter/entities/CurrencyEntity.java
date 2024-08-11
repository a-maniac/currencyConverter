package com.example.CurrencyConverter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public Double fromCurrencyExchangeRate;
    public String fromCurrency;
    public Double toCurrencyExchangeRate;
    public String toCurrency;
    public Double calculatedCurrency;
    public Double units;
}
