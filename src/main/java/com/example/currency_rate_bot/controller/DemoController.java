package com.example.currency_rate_bot.controller;

import com.example.currency_rate_bot.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final CurrencyRateService service;

    @GetMapping
    public String runDemo() {
        service.syncCurrencyRates();
        return "Done!";
    }
}
