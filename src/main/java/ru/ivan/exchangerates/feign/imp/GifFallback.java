package ru.ivan.exchangerates.feign.imp;


import org.springframework.stereotype.Component;
import ru.ivan.exchangerates.feign.GifClient;

@Component
public class GifFallback implements GifClient {


    @Override
    public String getGif(String api_key, String q, Integer offset) {
        return null;
    }
}