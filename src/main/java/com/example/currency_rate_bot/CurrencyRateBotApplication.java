package com.example.currency_rate_bot;

import com.example.currency_rate_bot.service.CurrencyRateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class CurrencyRateBotApplication {
    static CurrencyRateServiceImpl service;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateBotApplication.class, args);
        service.syncCurrencyRates();
    }
}
