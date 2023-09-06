package com.example.currency_rate_bot.repository;

import com.example.currency_rate_bot.model.CurrencyRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    @Transactional
    default void updateCurrencyRates(List<CurrencyRate> currencyRates) {
        for (CurrencyRate rate : currencyRates) {
            CurrencyRate existingRate = findByCurrencyName(rate.getCurrencyName());
            if (existingRate != null) {
                existingRate.setRate(rate.getRate());
            } else {
                save(rate);
            }
        }
    }

    CurrencyRate findByCurrencyName(String currencyName);
}
