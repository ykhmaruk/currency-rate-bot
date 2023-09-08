package com.example.currency_rate_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CurrencyRateBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateBotApplication.class, args);
    }
}
