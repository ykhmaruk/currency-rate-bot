package com.example.currency_rate_bot.repository;

import com.example.currency_rate_bot.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
}
