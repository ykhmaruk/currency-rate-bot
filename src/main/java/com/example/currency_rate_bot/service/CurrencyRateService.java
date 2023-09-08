package com.example.currency_rate_bot.service;

public interface CurrencyRateService {
    void syncCurrencyRates();

    String get(String name);
}
