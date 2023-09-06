package com.example.currency_rate_bot.mapper;

import com.example.currency_rate_bot.config.MapperConfig;
import com.example.currency_rate_bot.dto.ApiCurrencyDto;
import com.example.currency_rate_bot.model.CurrencyRate;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CurrencyRateMapper {
    CurrencyRate mapToModel(ApiCurrencyDto dto);
}
