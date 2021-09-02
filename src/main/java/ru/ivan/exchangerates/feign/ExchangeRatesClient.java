package ru.ivan.exchangerates.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "rate", url = "${rates.url}")
public interface ExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET, path = {"/{date}.json"})
    String getExchangeRates(@RequestParam("app_id") String app_id, @PathVariable("date") String date);

}