package com.example.currency_rate_bot.dto;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ApiCurrencyDto {
    @SerializedName("txt")
    private String currencyName;
    private BigDecimal rate;
}
