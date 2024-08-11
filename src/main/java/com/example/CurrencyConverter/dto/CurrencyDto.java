package com.example.CurrencyConverter.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CurrencyDto {

    public Double fromCurrencyExchangeRate;
    public String fromCurrency;
    public Double toCurrencyExchangeRate;
    public String toCurrency;
    public Double calculatedCurrency;
    public Double units;
}
