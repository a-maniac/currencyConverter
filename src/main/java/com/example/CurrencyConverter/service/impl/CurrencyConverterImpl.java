package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.service.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {
    private final RestClient restClient;

    @Override
    public ResponseEntity convertCurrency(String fromCurrency, String toCurrency, Long units) {

        return restClient.get()
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

    }
}
