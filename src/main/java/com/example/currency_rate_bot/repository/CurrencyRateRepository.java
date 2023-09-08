package com.example.currency_rate_bot.repository;

import com.example.currency_rate_bot.model.CurrencyRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    @Transactional
    default void updateCurrencyRates(List<CurrencyRate> currencyRates) {
        for (CurrencyRate rate : currencyRates) {
            CurrencyRate existingRate = findByCurrencyNameIgnoreCase(rate.getCurrencyName());
            if (existingRate != null) {
                existingRate.setRate(rate.getRate());
            } else {
                save(rate);
            }
        }
    }

    @Query("SELECT cr FROM CurrencyRate cr WHERE LOWER(cr.currencyName) = LOWER(:currencyName)")
    CurrencyRate findByCurrencyNameIgnoreCase(@Param("currencyName") String currencyName);
}
