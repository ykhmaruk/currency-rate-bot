package com.example.currency_rate_bot.service;

import com.example.currency_rate_bot.dto.ApiCurrencyDto;
import com.example.currency_rate_bot.mapper.CurrencyRateMapper;
import com.example.currency_rate_bot.model.CurrencyRate;
import com.example.currency_rate_bot.repository.CurrencyRateRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyRateServiceImplTest {
    @Value("${nbu.api.url}")
    private String url;
    @Mock
    private CurrencyRateService currencyRateService;
    @Mock
    private HttpClient client;
    @Mock
    private CurrencyRateRepository repository;
    @Mock
    private CurrencyRateMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyRateService = new CurrencyRateServiceImpl(client, repository, mapper);
    }

    @Test
    void get_Ok() {
        CurrencyRate rate = new CurrencyRate();
        rate.setRate(BigDecimal.valueOf(40));
        rate.setCurrencyName("Євро");
        String date =
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String expected =
                "Офіційний курс української гривні до %s на дату: %s становить: %s".formatted(
                        rate.getCurrencyName(),
                        date,
                        rate.getRate());

        when(repository.findByCurrencyNameIgnoreCase(rate.getCurrencyName()))
                .thenReturn(rate);

        String actual = currencyRateService.get(rate.getCurrencyName());
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void syncCurrencyRates_Ok() {
        List<ApiCurrencyDto> apiCurrencyDtoList = new ArrayList<>();
        ApiCurrencyDto usdDto = new ApiCurrencyDto();
        usdDto.setRate(BigDecimal.valueOf(37));
        usdDto.setCurrencyName("Американьский долар");
        apiCurrencyDtoList.add(usdDto);

        ApiCurrencyDto euroDto = new ApiCurrencyDto();
        euroDto.setCurrencyName("Євро");
        euroDto.setRate(BigDecimal.valueOf(40));
        apiCurrencyDtoList.add(euroDto);

        List<CurrencyRate> currencyRateList = new ArrayList<>();
        CurrencyRate usd = new CurrencyRate();
        usd.setRate(BigDecimal.valueOf(37));
        usd.setCurrencyName("Американьский долар");
        currencyRateList.add(usd);

        CurrencyRate euro = new CurrencyRate();
        euro.setCurrencyName("Євро");
        euro.setRate(BigDecimal.valueOf(40));
        currencyRateList.add(euro);

        when(client.get(url, ApiCurrencyDto.class)).thenReturn(apiCurrencyDtoList);
        when(mapper.mapToModel(euroDto)).thenReturn(euro);
        when(mapper.mapToModel(usdDto)).thenReturn(usd);
        doNothing().when(repository).updateCurrencyRates(currencyRateList);

        currencyRateService.syncCurrencyRates();

        verify(client, times(1)).get(url, ApiCurrencyDto.class);
        verify(mapper, times(2)).mapToModel(any(ApiCurrencyDto.class));
        verify(repository, times(1)).updateCurrencyRates(currencyRateList);
    }
}