package com.example.currency_rate_bot.controller;

import com.example.currency_rate_bot.dto.ApiCurrencyDto;
import com.example.currency_rate_bot.service.HttpClient;
import java.util.List;
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
    private static final String url =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final HttpClient client;

    @GetMapping
    public String runDemo() {

        List<ApiCurrencyDto> list = client.get(url, ApiCurrencyDto.class);
        for (ApiCurrencyDto dto : list) {
            System.out.println(dto.toString());
        }
        return "Done!";
    }
}
