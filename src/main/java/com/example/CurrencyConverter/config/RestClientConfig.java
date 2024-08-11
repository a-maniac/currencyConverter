package com.example.CurrencyConverter.config;

import jdk.jfr.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient getRestClient(){
        return RestClient.builder()
                .baseUrl("https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_QXgaZo6KwSL4ymSAXzqt1DBdRZTEOilCGe1HnDuE")
                .defaultHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
                .build();
    }
}
