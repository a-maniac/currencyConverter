package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.service.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {
    private final RestClient restClient;

    @Override
    public Double convertCurrency(String fromCurrency, String toCurrency, Double units) {
        String uri="/v1/latest?apikey=fca_live_QXgaZo6KwSL4ymSAXzqt1DBdRZTEOilCGe1HnDuE&currencies=INR";
        Map<String, Map<String, Double>> currencyExchangeRate=restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Map<String, Double>>>() {
                });

        Map<String, Double> innerMap=currencyExchangeRate.get("data");

        return units/(innerMap.get("INR"));

    }
}
