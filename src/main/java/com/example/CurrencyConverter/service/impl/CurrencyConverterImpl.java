package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.dto.CurrencyDto;
import com.example.CurrencyConverter.entities.CurrencyEntity;
import com.example.CurrencyConverter.repositories.CurrencyRepository;
import com.example.CurrencyConverter.service.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {

    Logger log = LoggerFactory.getLogger(CurrencyConverterImpl.class);

    private final RestClient restClient;
    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CurrencyDto convertCurrency(String fromCurrency, String toCurrency, Double units) {
        log.info("Entering CurrencyConverterImpl.convertCurrency()");

        CurrencyEntity currencyEntity= new CurrencyEntity();

        String fromCurrencyUri="/v1/latest?apikey=fca_live_QXgaZo6KwSL4ymSAXzqt1DBdRZTEOilCGe1HnDuE&currencies="+fromCurrency;
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

        currencyEntity.setToCurrency(toCurrency);
        currencyEntity.setFromCurrency(fromCurrency);
        currencyEntity.setToCurrencyExchangeRate(innerMapToCurrency.get(toCurrency));
        currencyEntity.setFromCurrencyExchangeRate(innerMapFromCurrency.get(fromCurrency));
        currencyEntity.setCalculatedCurrency(convertedCurrency);
        currencyEntity.setUnits(units);
        log.info("Exiting from CurrencyConverterImpl.convertCurrency()");
        currencyRepository.save(currencyEntity);
        return modelMapper.map(currencyEntity, CurrencyDto.class);

    }
}
