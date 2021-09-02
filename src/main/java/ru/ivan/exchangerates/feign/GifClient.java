package ru.ivan.exchangerates.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gif", url = "${gifs.url}")
public interface GifClient {

    @RequestMapping(method = RequestMethod.GET)
    String getGif(
            @RequestParam("api_key") String app_key,
            @RequestParam("q") String q,
            @RequestParam("offset") Integer offset
    );

}