package com.example.CurrencyConverter.controllers;

import com.example.CurrencyConverter.dto.CurrencyDto;
import com.example.CurrencyConverter.entities.CurrencyEntity;
import com.example.CurrencyConverter.service.impl.CurrencyConverterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/convertCurrency")
@RequiredArgsConstructor
public class CurrencyController {

    @Autowired
    CurrencyConverterImpl currencyConverter;

    @GetMapping(path="/getCurrency")
    public CurrencyDto getExchangeRate(@RequestParam String fromCurrency,
                                       @RequestParam String toCurrency,
                                       @RequestParam Double units) {

        return currencyConverter.convertCurrency(fromCurrency,toCurrency,units);
    }
}
