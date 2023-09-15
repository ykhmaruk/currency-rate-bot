package com.example.currency_rate_bot.service;

import com.example.currency_rate_bot.dto.ApiCurrencyDto;
import com.example.currency_rate_bot.mapper.CurrencyRateMapper;
import com.example.currency_rate_bot.model.CurrencyRate;
import com.example.currency_rate_bot.repository.CurrencyRateRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class CurrencyRateServiceImpl implements CurrencyRateService {
    @Value("${nbu.api.url}")
    private String url;
    private final HttpClient client;
    private final CurrencyRateRepository repository;
    private final CurrencyRateMapper mapper;

    @Scheduled(cron = "0 7 * * * ?")
    @Override
    public void syncCurrencyRates() {
        List<CurrencyRate> list = client.get(url, ApiCurrencyDto.class).stream().map(
                mapper::mapToModel).toList();
       repository.updateCurrencyRates(list);
    }

    @Override
    public String get(String name) {
        CurrencyRate rate = repository.findByCurrencyNameIgnoreCase(name);
        String date =
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return "Офіційний курс української гривні до %s на дату: %s становить: %s".formatted(
                rate.getCurrencyName(),
                date,
                rate.getRate());
    }
}
