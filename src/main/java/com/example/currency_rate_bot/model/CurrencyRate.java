package com.example.currency_rate_bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "create-currency-rate-seq")
    @SequenceGenerator(name = "create-currency-rate-seq",
            sequenceName = "public.currency_rate_id_seq",
            allocationSize = 1)
    private Long id;
    private String currencyName;
    private BigDecimal rate;
}
