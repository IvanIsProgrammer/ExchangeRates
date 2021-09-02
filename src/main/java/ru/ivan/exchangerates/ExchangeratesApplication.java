package ru.ivan.exchangerates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"ru.ivan.exchangerates.feign",
		"ru.ivan.exchangerates.controller"})
public class ExchangeratesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeratesApplication.class, args);
	}

}
