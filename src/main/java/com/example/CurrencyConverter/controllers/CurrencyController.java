package com.example.CurrencyConverter.controllers;

import com.example.CurrencyConverter.service.impl.CurrencyConverterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getExchangeRate(@RequestParam String fromCurrency,
                                          @RequestParam String toCurrency,
                                          @RequestParam Long units) {
        ResponseEntity currency=currencyConverter.convertCurrency(fromCurrency,toCurrency,units);

        return currency;
    }
}
