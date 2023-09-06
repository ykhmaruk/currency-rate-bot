package com.example.currency_rate_bot.mapper;

import com.example.currency_rate_bot.dto.ApiCurrencyDto;
import com.example.currency_rate_bot.model.CurrencyRate;

public class CurrencyRateMapper {
    public CurrencyRate mapToEntity(ApiCurrencyDto dto) {
        CurrencyRate rate = new CurrencyRate();
        rate.setRate(dto.getRate());
        rate.setCurrencyName(dto.getCurrencyName());
        return rate;
    }
}
