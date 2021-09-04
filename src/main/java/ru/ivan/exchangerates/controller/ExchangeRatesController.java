package ru.ivan.exchangerates.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ivan.exchangerates.feign.ExchangeRatesClient;
import ru.ivan.exchangerates.feign.GifClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ExchangeRatesController {
    @Autowired
    private Environment env;

    private final ExchangeRatesClient exchangeRatesClient;
    private final GifClient gifClient;

    @Autowired
    public ExchangeRatesController(ExchangeRatesClient exchangeRatesClient, GifClient gifClient) {
        this.exchangeRatesClient = exchangeRatesClient;
        this.gifClient = gifClient;
    }

    @GetMapping(path = "/rate", produces = MediaType.APPLICATION_JSON_VALUE)
    String getGif(@RequestParam(name = "currency", defaultValue = "${rates.default}") String currency) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar;

        calendar = Calendar.getInstance();
        Double exchangeRateToday = getRate(
                dateFormat.format(calendar.getTime()),
                currency);


        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Double exchangeRateYesterday = getRate(
                dateFormat.format(calendar.getTime()),
                currency);

        if (exchangeRateToday == null || exchangeRateYesterday == null)
            return makeResponse("Invalid ISO currency code!", "", "");

        Double exchangeRateDelta = exchangeRateToday - exchangeRateYesterday;
        String rate;
        String url;
        if (exchangeRateToday - exchangeRateYesterday >= 0) {
            url = getRandomGifUrl("rich");
            rate = "Rich";
        }
        else {
            url = getRandomGifUrl("broke");
            rate = "Broke";
        }

        if (url == null) {
            return makeResponse("Invalid gif url!", rate, "");
        } else {
            return makeResponse("Reply received successfully!", rate, url);
        }
    }


    private Double getRate(String date, String currency) {
        /*
        Для указания базовой волюты нужна ПЛАТНАЯ подписка на openexchangerates.org!
        Я высчитываю курс валюты исходя из базового курса Доллара США (USD), так как ключ бесплатный.
         */

        String response = exchangeRatesClient.getExchangeRates(env.getProperty("rates.app_id"), date);
        try {
            JSONObject jObject = new JSONObject(response).getJSONObject("rates");
            Double baseRate = jObject.getDouble(env.getProperty("rates.base"));
            Double rate = jObject.getDouble(currency);
            return baseRate / rate;
        } catch (JSONException e) {
            return null;
        }
    }


    private String getRandomGifUrl(String q) {
        Integer offset = new Random().nextInt(50);
        String response = gifClient.getGif(env.getProperty("gifs.api_key"), q, offset);
        try {
            return new JSONObject(response)
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getJSONObject("images")
                    .getJSONObject("original")
                    .getString("url");
        } catch (JSONException e) {
            return null;
        }
    }


    private String makeResponse(String message, String rate, String url) {

        return new JSONObject()
                .put("message", message)
                .put("rate", rate)
                .put("url", url)
                .toString();
    }
}
