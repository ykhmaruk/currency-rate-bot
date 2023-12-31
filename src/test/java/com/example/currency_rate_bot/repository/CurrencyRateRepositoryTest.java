package com.example.currency_rate_bot.repository;

import com.example.currency_rate_bot.model.CurrencyRate;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CurrencyRateRepositoryTest {
    @Container
    public static PostgreSQLContainer<?> database =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("springboot")
                    .withPassword("springboot")
                    .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Autowired
    private CurrencyRateRepository repository;

    @Test
    @Sql("/scripts/add_three_currency.sql")
    void findByCurrencyNameIgnoreCase_Ok() {
        CurrencyRate actual = repository.findByCurrencyNameIgnoreCase("євро");
        CurrencyRate secondActual = repository.findByCurrencyNameIgnoreCase("ЗЛОТИЙ");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getRate(), BigDecimal.valueOf(40));
        Assertions.assertEquals(actual.getCurrencyName(), "Євро");

        Assertions.assertNotNull(secondActual);
        Assertions.assertEquals(secondActual.getRate(), BigDecimal.valueOf(10));
        Assertions.assertEquals(secondActual.getCurrencyName(), "Злотий");
    }

    @Test
    @Sql("/scripts/add_three_currency.sql")
    void findByCurrencyNameIgnoreCase_InvalidData_NotOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
            repository.findByCurrencyNameIgnoreCase("qwerty").getRate());
    }

    @Test
    @Sql("/scripts/add_three_currency.sql")
    void updateCurrencyRates_Ok() {
        CurrencyRate eur = new CurrencyRate();
        eur.setRate(BigDecimal.valueOf(45));
        eur.setCurrencyName("Євро");

        repository.updateCurrencyRates(List.of(eur));
        CurrencyRate actual = repository.findByCurrencyNameIgnoreCase("Євро");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getRate(), BigDecimal.valueOf(45));
    }
}