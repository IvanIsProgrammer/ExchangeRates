package ru.ivan.exchangerates.feign.imp;


import org.springframework.stereotype.Component;
import ru.ivan.exchangerates.feign.ExchangeRatesClient;

@Component
public class ExchangeRatesFallback implements ExchangeRatesClient {

    @Override
    public String getExchangeRates(String app_id, String date) {
        return null;
    }
}