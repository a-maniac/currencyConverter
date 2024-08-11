package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.service.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {

    Logger log = LoggerFactory.getLogger(CurrencyConverterImpl.class);

    private final RestClient restClient;

    @Override
    public Double convertCurrency(String fromCurrency, String toCurrency, Double units) {
        log.info("Entering CurrencyConverterImpl.convertCurrency()");

        String fromCurrencyUri="/v1/latest?apikey=fca_live_QXgaZo6KwSL4ymSAXzqt1DBdRZTEOilCGe1HnDuE&currencies=INR";
        Map<String, Map<String, Double>> fromCurrencyExchangeRate=restClient.get()
                .uri(fromCurrencyUri)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Map<String, Double>>>() {
                });

        String toCurrencyUri="/v1/latest?apikey=fca_live_QXgaZo6KwSL4ymSAXzqt1DBdRZTEOilCGe1HnDuE&currencies="+toCurrency;
        Map<String, Map<String, Double>> toCurrencyExchangeRate=restClient.get()
                .uri(toCurrencyUri)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Map<String, Double>>>() {
                });

        Map<String, Double> innerMapFromCurrency=fromCurrencyExchangeRate.get("data");
        Map<String, Double> innerMapToCurrency=toCurrencyExchangeRate.get("data");

        Double convertedCurrency=units*
                (innerMapToCurrency.get(toCurrency)/innerMapFromCurrency.get(fromCurrency));

        log.info("Exiting from CurrencyConverterImpl.convertCurrency()");
        return convertedCurrency;

    }
}
