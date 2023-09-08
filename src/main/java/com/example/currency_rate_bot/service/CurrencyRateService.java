package com.example.currency_rate_bot.service;

import com.example.currency_rate_bot.model.CurrencyRate;

public interface CurrencyRateService {
    void syncCurrencyRates();

    String get(String name);
}
